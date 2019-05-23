package com.zhiyi.chinaipo.models.entity;

/**
 * 用户信息
 */
//@Entity(nameInDb = "UserEntity")
public class UserEntity {
        private String name;

        private String function;

        private String description;

        private String weixin_token;

        private String weibo_token;

        private String qq_token;

        private String company;

        private String department;

        private String classify;

        private String focus;

        private String area;

        private String stocks;

        private String extra;

        private String occupation;

        private String fullname;

        private String avatar;

        private String mobile;

        private String email;

        private String phone;

        private String photo;

        private String created;

        private String lastlogin;

        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setFunction(String function){
            this.function = function;
        }
        public String getFunction(){
            return this.function;
        }
        public void setDescription(String description){
            this.description = description;
        }
        public String getDescription(){
            return this.description;
        }
        public void setWeixin_token(String weixin_token){
            this.weixin_token = weixin_token;
        }
        public String getWeixin_token(){
            return this.weixin_token;
        }
        public void setWeibo_token(String weibo_token){
            this.weibo_token = weibo_token;
        }
        public String getWeibo_token(){
            return this.weibo_token;
        }
        public void setQq_token(String qq_token){
            this.qq_token = qq_token;
        }
        public String getQq_token(){
            return this.qq_token;
        }
        public void setCompany(String company){
            this.company = company;
        }
        public String getCompany(){
            return this.company;
        }
        public void setDepartment(String department){
            this.department = department;
        }
        public String getDepartment(){
            return this.department;
        }
        public void setClassify(String classify){
            this.classify = classify;
        }
        public String getClassify(){
            return this.classify;
        }
        public void setFocus(String focus){
            this.focus = focus;
        }
        public String getFocus(){
            return this.focus;
        }
        public void setArea(String area){
            this.area = area;
        }
        public String getArea(){
            return this.area;
        }
        public void setStocks(String stocks){
            this.stocks = stocks;
        }
        public String getStocks(){
            return this.stocks;
        }
        public void setExtra(String extra){
            this.extra = extra;
        }
        public String getExtra(){
            return this.extra;
        }
        public void setOccupation(String occupation){
            this.occupation = occupation;
        }
        public String getOccupation(){
            return this.occupation;
        }
        public void setFullname(String fullname){
            this.fullname = fullname;
        }
        public String getFullname(){
            return this.fullname;
        }
        public void setAvatar(String avatar){
            this.avatar = avatar;
        }
        public String getAvatar(){
            return this.avatar;
        }
        public void setMobile(String mobile){
            this.mobile = mobile;
        }
        public String getMobile(){
            return this.mobile;
        }
        public void setEmail(String email){
            this.email = email;
        }
        public String getEmail(){
            return this.email;
        }
        public void setPhone(String phone){
            this.phone = phone;
        }
        public String getPhone(){
            return this.phone;
        }
        public void setPhoto(String photo){
            this.photo = photo;
        }
        public String getPhoto(){
            return this.photo;
        }
        public void setCreated(String created){
            this.created = created;
        }
        public String getCreated(){
            return this.created;
        }
        public void setLastlogin(String lastlogin){
            this.lastlogin = lastlogin;
        }
        public String getLastlogin(){
            return this.lastlogin;
        }


  /*  // 自增长ID
    @Id(autoincrement = true)
    private Long id;
    //ID
    @SerializedName("userid")
    private String userId;
    //账号
    private String account;
    //密码
    private String password;
    //名字
    @SerializedName("realname")
    private String name;
    //性别
    @SerializedName("gender")
    private String gender;
    //昵称
    @SerializedName("nickname")
    private String nickname;
    //移动电话
    @SerializedName("mobile")
    private String mobilePhone;

    @SerializedName("email")
    private String email;

    //头像
    @SerializedName("avatar")
    private String avatarUrl;
    //地区、地址
    @SerializedName("area")
    private String area;
    //积分
    @SerializedName("point")
    private String point;
    //签名
    @SerializedName("signature")
    private String signature;

    //Token
    @SerializedName("access_token")
    private String token;

    @SerializedName("desc")
    private String description;

    @SerializedName("photo")
    private String photoUrl;

    @SerializedName("company")
    private String companyName;

    @SerializedName("department")
    private String department;

    @SerializedName("classify")
    private String classify;

    @SerializedName("focus")
    private String focused;

    @SerializedName("stocks")
    private String stocks;

    @SerializedName("fullname")
    private String fullName;

    @SerializedName("qq_token")
    private String qqToken;

    @SerializedName("wbToken")
    private String wbToken;

    @SerializedName("wxToken")
    private String wxToken;


    @Generated(hash = 945839283)
    public UserEntity(Long id, String userId, String account, String password, String name, String gender,
            String nickname, String mobilePhone, String email, String avatarUrl, String area, String point,
            String signature, String token, String description, String photoUrl, String companyName,
            String department, String classify, String focused, String stocks, String fullName, String qqToken,
            String wbToken, String wxToken) {
        this.id = id;
        this.userId = userId;
        this.account = account;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.nickname = nickname;
        this.mobilePhone = mobilePhone;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.area = area;
        this.point = point;
        this.signature = signature;
        this.token = token;
        this.description = description;
        this.photoUrl = photoUrl;
        this.companyName = companyName;
        this.department = department;
        this.classify = classify;
        this.focused = focused;
        this.stocks = stocks;
        this.fullName = fullName;
        this.qqToken = qqToken;
        this.wbToken = wbToken;
        this.wxToken = wxToken;
    }

    @Generated(hash = 1433178141)
    public UserEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getFocused() {
        return focused;
    }

    public void setFocused(String focused) {
        this.focused = focused;
    }

    public String getStocks() {
        return stocks;
    }

    public void setStocks(String stocks) {
        this.stocks = stocks;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getQqToken() {
        return qqToken;
    }

    public void setQqToken(String qqToken) {
        this.qqToken = qqToken;
    }

    public String getWbToken() {
        return wbToken;
    }

    public void setWbToken(String wbToken) {
        this.wbToken = wbToken;
    }

    public String getWxToken() {
        return wxToken;
    }

    public void setWxToken(String wxToken) {
        this.wxToken = wxToken;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", nickname='" + nickname + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", email='" + email + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", area='" + area + '\'' +
                ", point='" + point + '\'' +
                ", signature='" + signature + '\'' +
                ", token='" + token + '\'' +
                ", description='" + description + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", companyName='" + companyName + '\'' +
                ", department='" + department + '\'' +
                ", classify='" + classify + '\'' +
                ", focused='" + focused + '\'' +
                ", stocks='" + stocks + '\'' +
                ", fullName='" + fullName + '\'' +
                ", qqToken='" + qqToken + '\'' +
                ", wbToken='" + wbToken + '\'' +
                ", wxToken='" + wxToken + '\'' +
                '}';
    }*/
}