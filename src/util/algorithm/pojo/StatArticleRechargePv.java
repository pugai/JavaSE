package util.algorithm.pojo;

/**
 * @Description
 * @Auther ctl
 * @Date 2019/7/18
 */
public class StatArticleRechargePv {

    private String articleUuid;
    private Long pv;

    public String getArticleUuid() {
        return articleUuid;
    }

    public void setArticleUuid(String articleUuid) {
        this.articleUuid = articleUuid;
    }

    public Long getPv() {
        return pv;
    }

    public void setPv(Long pv) {
        this.pv = pv;
    }

    @Override
    public String toString() {
        return "StatArticleRechargePv{" +
                "articleUuid='" + articleUuid + '\'' +
                ", pv=" + pv +
                '}';
    }
}
