package com.example.zhb.study.demo.message.support;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/***
 *
 * @author Xiaojie.Li
 * @description 基础监听器
 * @date 2021/5/31
 */
@Slf4j
public abstract class AbstractTodoListener implements MessageListener{

  /*  @Resource
    protected PushMessageService pushMessageService;*/

    @Resource
    protected MessageEventPublisher messageEventPublisher;

/*    @Resource
    protected IUserJoinService userJoinService;

    @Resource
    protected IUserInfoService userInfoService;*/

    @Override
    public boolean doMsg(MessageEvent event) {

        if (event != null) {
            if (event.getSource() == null) {
                log.error("source is empty");
                return false;
            }
            if (event instanceof AbstractRevocableTodoEvent) {
                if (!((AbstractRevocableTodoEvent) event).isWithdraw()) {
                    doDispatch(event);
                } else {
                    doWithdraw(event);
                }
                return true;
            }
            doDispatch(event);
            return true;
        }
        log.error("event is null");
        return false;
    }

    protected abstract void withdraw(MessageEvent event);

    protected abstract void dispatch(MessageEvent event);

    private void doWithdraw(MessageEvent event) {
        log.info(this.getClass().getSimpleName() + "start handle " + event.getClass().getSimpleName() + "param:" + JSON.toJSONString(event) + "try " + event.getCurRetryTimes() + " time");
        withdraw(event);
        log.info(this.getClass().getSimpleName() + "end handle " + event.getClass().getSimpleName() + "param:" + JSON.toJSONString(event) + "try " + event.getCurRetryTimes() + " time");
    }



    private void doDispatch(MessageEvent event) {
        log.info(this.getClass().getSimpleName() + "start handle " + event.getClass().getSimpleName() + "param:" + JSON.toJSONString(event) + "try " + event.getCurRetryTimes() + " time");
        dispatch(event);
        log.info(this.getClass().getSimpleName() + "end handle " + event.getClass().getSimpleName() + "param:" + JSON.toJSONString(event) + "try " + event.getCurRetryTimes() + " time");
    }


    protected List<Long> filterHisFollower(List<Long> followingUserList) {
        Set<Long> userJoinHisSet = Sets.newHashSet();
        List<Long> filterList = Lists.newArrayList();
        for (Long centerId : followingUserList) {
            if (userJoinHisSet.contains(centerId)){
                continue;
            }
            filterList.add(centerId);
        }
        return filterList;
    }

}
