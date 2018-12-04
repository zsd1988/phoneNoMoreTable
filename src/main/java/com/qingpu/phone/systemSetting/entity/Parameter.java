package com.qingpu.phone.systemSetting.entity;

import com.qingpu.phone.common.utils.UUIDGenerator;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="parameter")
public class Parameter {

    @Id
    @Column(length = 32)
    private String id = UUIDGenerator.getUUID(); // 主键Id

    private Date create_time = new Date();

    private Boolean is_del = false;

    @Column(length = 50)
    private String name;	//名称

    @Column(length = 32)
    @Index(name="I_parameter_code")
    private String code;	//编码（作为同一种参数的唯一标识码）

    @Column(length = 1000)
    private String content;		//内容

    @Column
    @Enumerated(value = EnumType.STRING)
    private DataType data_type;	//内容数据类型，作为校验。


    public enum DataType {
        INTEGER("整型"), // INTEGER
        STRING("字符串"), // STRING
        FLOAT("浮点型"), // FLOAT
        DATETIME("日期时间"), // DATETIME
        TIME("时间"); // TIME

        private String dataType;
        DataType(String dataType) {
            this.dataType=dataType;
        }
        public String getName() {
            return dataType;
        }
        public String getType(){
            return this.toString();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DataType getDataType() {
        return data_type;
    }

    public void setDataType(DataType data_type) {
        this.data_type = data_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return create_time;
    }

    public void setCreateTime(Date create_time) {
        this.create_time = create_time;
    }

    public Boolean getIsDel() {
        return is_del;
    }

    public void setIsDel(Boolean is_del) {
        this.is_del = is_del;
    }
}
