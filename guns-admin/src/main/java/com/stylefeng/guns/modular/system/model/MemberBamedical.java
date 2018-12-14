package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 会员健康状态关系表
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-17
 */
@TableName("main_member_bamedical")
public class MemberBamedical extends Model<MemberBamedical> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer memberid;
    private Integer bamedicalid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public Integer getBamedicalid() {
        return bamedicalid;
    }

    public void setBamedicalid(Integer bamedicalid) {
        this.bamedicalid = bamedicalid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MemberBamedical{" +
        "id=" + id +
        ", memberid=" + memberid +
        ", bamedicalid=" + bamedicalid +
        "}";
    }
}
