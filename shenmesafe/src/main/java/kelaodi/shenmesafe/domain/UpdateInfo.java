package kelaodi.shenmesafe.domain;

/**
 * Created by Administrator on 2015/4/17.
 */
public class UpdateInfo {
    private String version;
    private String description;
    private String apkurl;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApkurl() {
        return apkurl;
    }

    public void setApkurl(String apkurl) {
        this.apkurl = apkurl;
    }
}
