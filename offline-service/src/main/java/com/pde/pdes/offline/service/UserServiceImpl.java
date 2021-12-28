package com.pde.pdes.offline.service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pde.pdes.base.dto.SimpleResponse;
import com.pde.pdes.offline.dto.UserccDTO;
import com.pde.pdes.offline.po.UserApproveDocument;
import com.pde.pdes.offline.utils.JwtUtil;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pde.pdes.offline.mapper.UserMapper;
import com.pde.pdes.offline.po.UserPO;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService{

    /**
     * 获取用户，并登录（第一次登录设定初始密码）
     * @param request
     * @param username
     * @param password
     * @return
     */
    @Override
    public SimpleResponse<Object> getUser(HttpServletRequest request, String username, String password) {
        QueryWrapper<UserPO> qw = new QueryWrapper<UserPO>();
        qw.lambda().eq(UserPO::getLoginname,username);
        UserPO userPo = this.getOne(qw);
        SimpleResponse<Object> res = new SimpleResponse<Object>();
        if (null != userPo && userPo.getPassword().equals("")) {
            LambdaUpdateWrapper<UserPO> wrapper = Wrappers.lambdaUpdate(UserPO.class)
                    .set(UserPO::getPassword,password)
                    .eq(UserPO::getLoginname,username);
            this.update(wrapper);
            res.setCode("PDE-201");
            try {
                String token = JwtUtil.createToken("d4dd212ccc7145448ee717c81b2d916a",username);
                res.setContent(token);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }  else if (null != userPo && userPo.getPassword().equals(password)) {
            try {
                String token = JwtUtil.createToken("d4dd212ccc7145448ee717c81b2d916a",username);
                res.setContent(token);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        } else {
            res.setStatus(false);
            return res;
        }
    }

    /**
     * 检验token
     * @param request
     * @return
     */
    @Override
    public SimpleResponse<Object> validateToken(HttpServletRequest request) {
        SimpleResponse<Object> res = new SimpleResponse<Object>();
        String token = request.getHeader("token");
        if (!StringUtils.isEmpty(token) && !token.equals("null")) {
            try {
                Map<String, Claim> jwtMap =  JwtUtil.verifyToken(token);
                if (null != jwtMap) {
                    String  loginName = jwtMap.get("loginName").asString();
                    QueryWrapper<UserPO> qw = new QueryWrapper<UserPO>();
                    qw.lambda().eq(UserPO::getLoginname,loginName);
                    UserPO userPo = this.getOne(qw);
                    if (null != userPo) {
                        res.setContent(userPo);
                    } else {
                        res.setStatus(false);
                    }
                } else {
                    res.setStatus(false);
                    res.setMessage("无效token");
                }
            } catch (Exception e) {
                res.setStatus(false);
                res.setMessage("token已过期，请重新登录");
                return res;
            }
        } else {
            res.setStatus(false);
            res.setMessage("token为空");
        }
        return res;
    }

}
