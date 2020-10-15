package hello.entity;

import java.util.List;

public class BlogResult extends Result<List<Blog>> {
    private Integer total;
    private Integer page;
    private Integer totalPage;

    public static BlogResult newBlogs(List<Blog> data, Integer total, Integer page, Integer totalPage) {
        return new BlogResult(data, total, page, totalPage);
    }

    public BlogResult(List<Blog> data, Integer total, Integer page, Integer totalPage) {
        super("ok", "获取成功", data);
        this.page = page;
        this.total = total;
        this.totalPage = totalPage;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getTotalPage() {
        return totalPage;
    }
}
