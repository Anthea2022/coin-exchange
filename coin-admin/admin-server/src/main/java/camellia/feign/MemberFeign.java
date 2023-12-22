package camellia.feign;

import camellia.common.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.NotNull;

/**
 * @author 墨染盛夏
 * @version 2023/12/22 15:26
 */
@FeignClient(name = "member-server", configuration = OAuth2FeignConfig.class)
public interface MemberFeign {
    /**
     * 根据审核状态查看认证信息
     * @param uid
     * @param reviewStatus 0待审核1同意2拒绝
     * @return
     */
    @GetMapping("/senior/auth/detail")
    BaseResponse<Object> getInfoDetail(@NotNull Long uid, @NotNull Byte reviewStatus);

    /**
     * 查看用户的基本信息
     * @param pageSize
     * @param pageNum
     * @param reviewStatus
     * @param name
     * @param realName
     * @param phone
     * @param email
     * @return
     */
    @GetMapping("/info/list")
    BaseResponse<Object> listBasicInfo(@NotNull Integer pageSize, @NotNull Integer pageNum, Integer reviewStatus,
                                         String name, String realName, String phone, String email);

    /**
     * 审核用户认证
     * @param authCode
     * @param status
     * @param remark
     * @return
     */
    @PostMapping("/senior/auth/check")
    BaseResponse<Object> updateReviewStatus(@NotNull Long authCode, @NotNull Byte status, String remark);

    /**
     * 设置用户状态
     * @param uid
     * @param status
     * @return
     */
    @PostMapping("/status/set")
    BaseResponse<Object> setStatus(@NotNull Long uid, @NotNull Byte status);
}
