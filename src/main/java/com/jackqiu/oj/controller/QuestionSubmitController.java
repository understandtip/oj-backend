package com.jackqiu.oj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jackqiu.oj.common.BaseResponse;
import com.jackqiu.oj.common.ErrorCode;
import com.jackqiu.oj.common.ResultUtils;
import com.jackqiu.oj.exception.BusinessException;
import com.jackqiu.oj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.jackqiu.oj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.jackqiu.oj.model.entity.QuestionSubmit;
import com.jackqiu.oj.model.entity.User;
import com.jackqiu.oj.model.vo.QuestionSubmitVO;
import com.jackqiu.oj.service.QuestionSubmitService;
import com.jackqiu.oj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 题目提交接口
 *
 *  
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return resultNum
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
            HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能提交题目
        final User loginUser = userService.getLoginUser(request);
        Long result = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(result);
    }

    /**
     * 分页获取用户列表（仅管理员）
     *   仅本人和管理员能看见自己(提交userId和登录用户id不同)提交的代码
     *
     * @param questionSubmitQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                      HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
//        String redisKey = null;
//        ValueOperations<String, Object> ops = null;
//        if (current == 1) {
//            redisKey = "oj:questionSubmit:listPage:" + size + ":" + current;
//            //1.1先从缓存中取数据
//            ops = redisTemplate.opsForValue();
//            Page<QuestionSubmitVO> questionSubmitVoPageInRedis = (Page<QuestionSubmitVO>) ops.get(redisKey);
//            //1.2如果存在直接返回
//            if (questionSubmitVoPageInRedis != null) {
//                return ResultUtils.success(questionSubmitVoPageInRedis);
//            }
//        }
        //2.查询出信息
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        //查询出当前登录了的用户信息
        final User loginUser = userService.getLoginUser(request);
        //返回脱敏信息
//        Page<QuestionSubmitVO> questionSubmitVOPage = questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser);
//        //3.将数据放在缓存中,注意设置缓存时间
//        if (current == 1) {
//            ops.set(redisKey,questionSubmitVOPage,1, TimeUnit.HOURS);
//        }
//        return ResultUtils.success(questionSubmitVOPage);
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));
    }
}
