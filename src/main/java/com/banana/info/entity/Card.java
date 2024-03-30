package com.banana.info.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjy
 * @since 2023-12-19
 */
@TableName("t_card")
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会员卡id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 会员卡类型
     */
    private String type;

    /**
     * 费用(元)
     */
    private Integer fee;

    /**
     * 描述
     */
    private String description;

    /**
     * 逻辑删除
     */
    private Integer deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Card{" +
            "id = " + id +
            ", type = " + type +
            ", fee = " + fee +
            ", description = " + description +
            ", deleted = " + deleted +
        "}";
    }
}
