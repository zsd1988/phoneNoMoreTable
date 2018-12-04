package test.testTable.entity;

import com.qingpu.phone.common.utils.UUIDGenerator;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="test_table")
public class TestTable implements Serializable {

    @Id
    @GeneratedValue
    Long id;

    private Date create_time = new Date();

    private Boolean is_del = false;

    private Date del_time;

    private Integer operator_id;     // 操作者id：如新建或删除

    @Enumerated(value = EnumType.STRING)
    private TestEnum content_type = TestEnum.URL;		// 测试枚举类型

    @Index(name = "num")
    @NotNull(message = "编号不能为空")
    @Column(unique = true)
    private Integer num;    // 编号


    public enum TestEnum {
        URL("链接"),
        Document("内容");
        private String name;
        TestEnum(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType(){
            return this.toString();
        }
    }

    public Date getDelTime() {
        return del_time;
    }

    public void setDelTime(Date del_time) {
        this.del_time = del_time;
    }

    public Integer getOperatorId() {
        return operator_id;
    }

    public void setOperatorId(Integer operator_id) {
        this.operator_id = operator_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public TestEnum getContentType() {
        return content_type;
    }

    public void setContentType(TestEnum content_type) {
        this.content_type = content_type;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
