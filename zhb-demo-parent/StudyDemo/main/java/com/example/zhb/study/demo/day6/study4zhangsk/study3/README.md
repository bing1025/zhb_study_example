SpringBoot | 常见几种异常配置方案 
https://blog.csdn.net/woshilijiuyi/article/details/78866546

在使用spring、springBoot的项目中，常见的配置大概有以下三种方案：

1）使用@ControllerAdvice注解，自定义 exceptionHandler，继承 ResponseEntityExceptionHandler，可以配置多个exceptionHandler。
在上篇的异常解析流程中，我们已经提到了，异常解析会先匹配 exceptionHandler中所标识的异常：
@ExceptionHandler(NotFoundException.class)

完整配置如下：
/**
 * Created by zhangshukang on 2017/08/31.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Value("${spring.application.name:}")
    private String systemName;

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity<MessageVo> handleBadRequestException(HttpServletRequest request, HttpServletResponse response, BadRequestException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessageList(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseEntity<MessageVo> handleBusinessException(HttpServletRequest request, HttpServletResponse response, BusinessException ex) {
        log.error(ErrorLogInfo.build(ex.getSrcClass(), systemName, ex.getErrorCode(), ex.getMessage()).toJson());
        Throwable orgEx = ex.getOriginalException();
        if (orgEx != null)
            log.error(orgEx.getMessage(), orgEx);
        else
            log.error(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessageList());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResponseEntity<MessageVo> handleUnauthorizedException(HttpServletRequest request, HttpServletResponse response, UnauthorizedException ex) {
        log.error(ErrorLogInfo.build(ex.getSrcClass(), systemName, ex.getErrorCode(), ex.getMessage(), ex.getLoginId()).toJson());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessageList(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<MessageVo> handleNotFoundException(HttpServletRequest request, HttpServletResponse response, NotFoundException ex) {
        log.warn(ex.getMessage(), ex);
        if (ex.getMessageList() == null) {
            NotFoundException ne = new NotFoundException(BaseException.ERR_9996, this);
            return new ResponseEntity<>(ne.getMessageList(), HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(ex.getMessageList(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseBody
    public ResponseEntity<MessageVo> handleForbiddenException(HttpServletRequest request, HttpServletResponse response, ForbiddenException ex) {
        log.error(ErrorLogInfo.build(ex.getSrcClass(), systemName, ex.getErrorCode(), ex.getMessage()).toJson());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessageList(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MovedPermanentlyException.class)
    @ResponseBody
    public ResponseEntity<MessageVo> handleMovedPermanentlyException(HttpServletRequest request, HttpServletResponse response, MovedPermanentlyException ex) {
        log.warn(ErrorLogInfo.build(ex.getSrcClass(), systemName, ex.getErrorCode(), ex.getMessage()).toJson());
        return new ResponseEntity<>(ex.getMessageList(), HttpStatus.MOVED_PERMANENTLY);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<MessageVo> handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        log.error(ErrorLogInfo.build(ex, systemName, BaseException.ERR_9999, ex.getMessage()).toJson());
        log.error(ex.getMessage(), ex);
        MessageVo messages = new MessageVo();
        messages.addMessageObj(BaseException.ERR_9999, ex.getMessage(), "");
        return new ResponseEntity<>(messages, HttpStatus.INTERNAL_SERVER_ERROR);
    }

2）在第一种情况没有找到exceptionHandler 或没有解析到对应的异常 的情况下，或根据全局错误路径，访问 AbstractErrorController。也可以使用默认的错误路径访问类：BasicErrorController 配置统一错误路径访问类：
@Controller
@Slf4j
public class GlobalExceptionController extends AbstractErrorController {
    private final ErrorProperties errorProperties;

    public GlobalExceptionController(){
        this(new DefaultErrorAttributes());
    }
    public GlobalExceptionController(ErrorAttributes errorAttributes) {
        this(errorAttributes, new ErrorProperties());
    }
    public GlobalExceptionController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        super(errorAttributes);
        Assert.notNull(errorProperties, "ErrorProperties must not be null");
        this.errorProperties = errorProperties;
    }

    private static final String PATH = "/error";

    @Override
    public String getErrorPath() {
        return PATH;
    }

    @RequestMapping("${server.error.path:${error.path:/error}}")
    @ResponseBody
    public ResponseEntity error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);
        String errMsg = getErrorMessage(request) != null ? getErrorMessage(request) : (String)body.get("message");
        log.error("url:{},error:{},message:{}", body.get("path"), body.get("error"), errMsg);
        if(status == HttpStatus.NOT_FOUND) {
            NotFoundException ne = new NotFoundException(BaseException.ERR_9996, this);
            return new ResponseEntity(ne.getMessageList(), status);
        } else {
            MessageVo msg = new MessageVo();
            msg.addMessageObj(BaseException.ERR_9999, errMsg, null);
            return new ResponseEntity(msg, status);
        }
    }

    protected ErrorProperties getErrorProperties() {
        return this.errorProperties;
    }

    protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
        ErrorProperties.IncludeStacktrace include = getErrorProperties().getIncludeStacktrace();
        if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
            return true;
        }
        if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
            return getTraceParameter(request);
        }
        return false;
    }

    private String getErrorMessage(HttpServletRequest request) {
        final Throwable exc = (Throwable) request.getAttribute("javax.servlet.error.exception");
        return exc != null ? exc.getMessage() : null;
    }
}

3）自定义的统一异常处理类：HandlerExceptionResolver 子类，是最后的异常处理策略。
 public class MyHandlerException implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object,
                                         Exception e) {
        ModelAndView mav = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("code", "500");
        attributes.put("message", "系统繁忙");
        view.setAttributesMap(attributes);
        view.setContentType("application/json;charset=UTF-8");
        mav.setView(view);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return mav;
    }
只有当 exceptionHandler 不存在 和 未定义带有 responseStatus的异常的情况下，
才会采用此异常策略。
例如抛出如下这种异常，不含responseStatus的情况下，才会进入自定义的统一异常解析类

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseException {

    private static final long serialVersionUID = -8687066286979480116L;

    public NotFoundException() {
        super();
    }
    public NotFoundException(MessageVo messages, Object srcClass) {
    	super();
    	setSrcClass(srcClass);
    	setMessageVo(messages);
    }

}

总结：尽量采用第一种和第二种结合的配置，对异常处理够灵活。以前公司的项目还是spring项目的时候，采用的是第三种，这样只能控制全局异常，而一些业务异常，解析后不能有一些自己的逻辑，例如，构造返回状态实体responseEntity的逻辑，不够灵活。
————————————————
版权声明：本文为CSDN博主「张书康」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/woshilijiuyi/article/details/78866546