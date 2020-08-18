package com.think.mybatis.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Accessors(chain = true)
@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -8402343759691572908L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    protected Long id;

    /**
     * 删除状态：0未删除，-1删除，默认未删除
     */
    @Column(name = "del_flag", length = 2)
    private Integer delFlag = 0;

    /**
     * 启用状态：0初始，1启用，2停用，默认启用
     */
    @Column(name = "enable_status", length = 2)
    private Integer enableStatus = 1;

    /**
     * 创建人
     */
    @Column(name = "created_by", length = 10)
    private Long createdBy;

    /**
     * 操作者
     */
    @Column(name = "updated_by", length = 10)
    private Long updatedBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @Column(name = "create_time", length = 19, updatable = false)
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @Column(name = "update_time", length = 19)
    private Date updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;
        BaseEntity that = (BaseEntity) o;
        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}
