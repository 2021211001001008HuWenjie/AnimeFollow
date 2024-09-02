package com.anime.service.impl;

import com.anime.domain.LoginUser;
import com.anime.domain.ResponseResult;
import com.anime.entity.User;
import com.anime.service.LoginService;
import com.anime.utils.BeanCopyUtils;
import com.anime.utils.JwtUtil;
import com.anime.utils.RedisCache;
import com.anime.vo.UserLoginVo;
import com.anime.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.Objects;
/**
 * @author 35238
 * @date 2024/9/1 21:16
 */
@Service
//认证，判断用户登录是否成功
public class LoginServiceImpl implements LoginService {
    @Autowired
//AuthenticationManager是security官方提供的接口
    private AuthenticationManager authenticationManager;
    @Autowired
//RedisCache是我们在huanf-framework工程的config目录写的类
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
//封装登录的用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
//在下一行之前，封装的数据会先走UserDetailsServiceImpl实现类，这个实现类在我们的huanf-framework工程的service/impl目录里面
        Authentication authenticate =
                authenticationManager.authenticate(authenticationToken);
//上面那一行会得到所有的认证用户信息authenticate。然后下一行需要判断用户认证是否通过，如果authenticate的值是null，就说明认证没有通过
        System.out.println(authenticate);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
//获取userid
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
//把这个userid通过我们写的JwtUtil工具类转成密文，这个密文就是token值
        String jwt = JwtUtil.createJWT(userId);
//下面那行的第一个参数: 把上面那行的jwt，也就是token值保存到Redis。存到时候是键值对的形式，值就是jwt，key要加上 "animelogin:" 前缀
//下面那行的第二个参数: 要把哪个对象存入Redis。我们写的是loginUser，里面有权限信息，后面会用到
        redisCache.setCacheObject("animelogin:"+userId,loginUser);
//把User转化为UserInfoVo，再放入vo对象的第二个参数
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        UserLoginVo vo = new UserLoginVo(jwt,userInfoVo);
//封装响应返回
        return ResponseResult.okResult(vo);
    }
}
