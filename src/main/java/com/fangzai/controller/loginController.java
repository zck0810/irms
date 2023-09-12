package com.fangzai.controller;

import com.fangzai.entity.MenuBar;
import com.fangzai.service.AuthorityService;
import net.sf.json.JSONObject;
import com.fangzai.entity.Result;
import com.fangzai.entity.User;
import com.fangzai.service.UserService;
import com.fangzai.utils.JwtUtils;
import com.fangzai.utils.VerifyCodeServlet;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RequestMapping("/loginController")
@RestController
public class loginController {

    @Resource
    private UserService userService;

    @Resource
    private AuthorityService authorityService;

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        User e = userService.login(user);
        //登录成功,生成令牌,下发令牌
        if (e != null){
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", e.getId());
            userInfo.put("loginId", e.getLoginId());
            userInfo.put("userName", e.getUserName());
            userInfo.put("unit", e.getUnit());
            userInfo.put("department", e.getDepartment());
            userInfo.put("roleId",e.getRoleId());
            userInfo.put("unitId",e.getUnitId());
            userInfo.put("departmentId",e.getDepartmentId());
            userInfo.put("userPermission", e.getUserPermission());
            userInfo.put("accountStatus", e.getAccountStatus());
            String jwt = JwtUtils.generateJwt(userInfo); //jwt包含了当前登录的员工信息
            return Result.success(jwt);
        }
        //登录失败, 返回错误信息
        return Result.error("用户名或密码错误");
    }

    @GetMapping("/getInfo")
    public Result getInfo(@RequestParam(value = "token") String token) {
        HashMap<String,String> map = new HashMap<>();
        Claims claims = JwtUtils.parseJWT(token);
        String loginId = claims.get("loginId").toString();
        String userName = claims.get("userName").toString();
        String unit = claims.get("unit").toString();
        String department = claims.get("department").toString();
        String userPermission = claims.get("userPermission").toString();
        String accountStatus = claims.get("accountStatus").toString();
        String roleId = claims.get("roleId").toString();
        String unitId = claims.get("unitId").toString();
        String departmentId = claims.get("departmentId").toString();
        map.put("loginId",loginId);
        map.put("userName",userName);
        map.put("unit",unit);
        map.put("department",department);
        map.put("userPermission",userPermission);
        map.put("accountStatus",accountStatus);
        map.put("roleId",roleId);
        map.put("unitId",unitId);
        map.put("departmentId",departmentId);
        return Result.success(map);
    }

    @PostMapping("logout")
    public Result logout(){
        return Result.success("系统已退出登录");
    }

    @GetMapping("/initVerifyCode")
    public JSONObject InitVerifyCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        VerifyCodeServlet verifyCodeServlet = new VerifyCodeServlet();
        verifyCodeServlet.init();
        BufferedImage buffImg = verifyCodeServlet.getBufferedImage();
        String vailitorCode = verifyCodeServlet.getVerifyCode();
        String verifyCode = ServletRequestUtils.getStringParameter(request, "fosV", "");
        request.getSession().setAttribute(verifyCode, vailitorCode);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(buffImg, "jpg", stream);
        Base64 base = new Base64();
        String base64 = base.encodeToString(stream.toByteArray());
        String base64_str = "data:image/png;base64," + base64;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",20000);
        jsonObject.put("data",base64_str);
        jsonObject.put("verifyCode",vailitorCode);
        return jsonObject;
    }

    @GetMapping("/getMoveRoute")
    public JSONObject getMoveRoute(@RequestParam(value = "accountStatus") String accountStatus, @RequestParam(value = "roleId") String roleId){
        StringBuilder returnStr = new StringBuilder();
        if("1".equals(accountStatus)){ //如果账号状态为1，表示该账号是冻结的
            returnStr = new StringBuilder("{\"code\":20003,\"message\":\"该用户已被冻结\",\"data\":\"freeze\"}");
        }else {
            List<MenuBar> menuBarDataList = authorityService.getRoleAuthorityByRoleid(roleId); //账号状态为0（正常），根据角色id查询相关的菜单栏数据
            if (menuBarDataList != null) { //如果菜单栏数据不为空，则处理菜单栏数据成为前端路由需要的格式
                String preMenuId = "";
                ArrayList<String> menuKeyList = new ArrayList<>();
                for (MenuBar menuBar : menuBarDataList) {
                    if (!preMenuId.equals(menuBar.getMenuId())) {//如果是新菜单组
                        menuKeyList.add(menuBar.getMenuId());
                        preMenuId = menuBar.getMenuId();
                    }
                }
                for (int i = 0; i < menuKeyList.size(); i++) {
                    String menuId = menuKeyList.get(i);
                    boolean flag = true;
                    for (int j = 0; j < menuBarDataList.size(); j++) {
                        MenuBar menuBar = menuBarDataList.get(j);
                        if (!menuBar.getMenuId().equals(menuId)) {
                            continue;
                        }
                        if (flag) {
                            String path = menuBar.getPath();
                            returnStr.append("{").append("\"path\":\"/")
                                    .append(path).append("\",")
                                    .append("\"component\":\"Layout\",")
                                    .append("\"name\":\"")
                                    .append(menuBar.getMenuName())
                                    .append("\",")
                                    .append("\"meta\": {\"title\":\"")
                                    .append(menuBar.getMenuName())
                                    .append("\",\"icon\":\"")
                                    .append(menuBar.getMenuIcon())
                                    .append("\" },")
                                    .append("\"children\": [");
                        }

                        flag = false;
                        returnStr.append("{ ")
                                .append("\"path\":\"")
                                .append(menuBar.getItemPath())
                                .append("\",").append("\"name\":\"")
                                .append(menuBar.getItemName())
                                .append("\",").append("\"component\":\"")
                                .append(menuBar.getPath())
                                .append("/").append(menuBar.getItemPath())
                                .append("\",")
                                .append("\"meta\": {\"title\":\"")
                                .append(menuBar.getItemName())
                                .append("\",\"icon\":\"\" }");
                        if (j == menuBarDataList.size() - 1) {
                            returnStr.append("}");
                        } else {
                            returnStr.append("},");
                        }

                    }
                    if (i == menuKeyList.size() - 1) {
                        returnStr.append("]}");
                    } else {
                        returnStr.append("]},");
                    }
                }
            }
            returnStr = new StringBuilder("{" +
                    "\"code\":20000," +
                    "\"data\":[" +
                    returnStr +
                    "]}");
        }
        return JSONObject.fromObject(returnStr.toString());
    }




}
