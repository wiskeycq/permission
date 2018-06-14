package com.cq.dao;

import com.cq.beans.PageQuery;
import com.cq.model.SysUser;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser findByKeyword(@Param("keyword") String keyword);

    int countByMail(@Param("mail") String mail,@Param("id") Integer id);

    int countByTelephone(@Param("telephone") String telephone,@Param("id") Integer id);

    int countByDeptId(@Param("deptId") Integer deptId);

    List<SysUser> getPageByDeptId(@Param("deptId") Integer deptId, @Param("page") PageQuery page);

    List<SysUser> getByIdList(@Param("idList") List<Integer> idList);

    List<SysUser> getAll();
}