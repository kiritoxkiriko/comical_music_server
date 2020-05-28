package wxm.example.comical_music_server.entity.respone;

import org.springframework.data.domain.Page;
import wxm.example.comical_music_server.constant.StatusCode;

import java.awt.print.Pageable;

/**
 * @author Alex Wang
 * @date 2020/05/12
 */
public class PageResponseData extends ResponseData{

    private Integer size;

    private Integer total;

    private Integer num;

    private Boolean hasNext;

    public PageResponseData() {
        super();
    }

    public PageResponseData(int code, String msg, Page page) {
        super(code, msg, page.toList());
        total=page.getTotalPages();
        size=page.getSize();
        num=page.getNumber();
        hasNext=page.hasNext();
    }

    public PageResponseData(StatusCode statusCode, Page page) {
        this(statusCode.getCode(),statusCode.getMsg(),page);
    }

    public static PageResponseData of(StatusCode statusCode, Page page){
        return new PageResponseData(statusCode.getCode(),statusCode.getMsg(),page);
    }

    public static PageResponseData success(Page page){
        return new PageResponseData(StatusCode.SUCCESS,page);
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    @Override
    public String toString() {
        return "PageResponseData{" +
                "size=" + size +
                ", total=" + total +
                ", num=" + num +
                ", hasNext=" + hasNext +
                "} " + super.toString();
    }
}
