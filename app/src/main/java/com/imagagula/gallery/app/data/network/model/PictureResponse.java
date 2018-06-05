package com.imagagula.gallery.app.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PictureResponse {

    @Expose
    @SerializedName("stat")
    private String statusCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("photos")
    private Photo photo;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public static class Photo {

        @Expose
        @SerializedName("page")
        private int page;

        @Expose
        @SerializedName("pages")
        private int pages;

        @Expose
        @SerializedName("perpage")
        private int perPage;

        @Expose
        @SerializedName("total")
        private int total;

        @Expose
        @SerializedName("photo")
        private List<Photos> data;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPerPage() {
            return perPage;
        }

        public void setPerPage(int perPage) {
            this.perPage = perPage;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<Photos> getData() {
            return data;
        }

        public void setData(List<Photos> data) {
            this.data = data;
        }

        public static class Photos {

            @Expose
            @SerializedName("id")
            private Long total;

            @Expose
            @SerializedName("owner")
            private String owner;

            @Expose
            @SerializedName("secret")
            private String secret;

            @Expose
            @SerializedName("latitude")
            private String latitude;

            @Expose
            @SerializedName("longitude")
            private String longitude;

            @Expose
            @SerializedName("server")
            private String server;

            @Expose
            @SerializedName("farm")
            private Long farm;

            @Expose
            @SerializedName("title")
            private String title;

            @Expose
            @SerializedName("url_sq")
            private String url;

            @Expose
            @SerializedName("accuracy")
            private String accuracy;

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public Long getTotal() {
                return total;
            }

            public void setTotal(Long total) {
                this.total = total;
            }

            public String getOwner() {
                return owner;
            }

            public void setOwner(String owner) {
                this.owner = owner;
            }

            public String getSecret() {
                return secret;
            }

            public void setSecret(String secret) {
                this.secret = secret;
            }

            public String getServer() {
                return server;
            }

            public void setServer(String server) {
                this.server = server;
            }

            public Long getFarm() {
                return farm;
            }

            public void setFarm(Long farm) {
                this.farm = farm;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getAccuracy() {
                return accuracy;
            }

            public void setAccuracy(String accuracy) {
                this.accuracy = accuracy;
            }
        }

      }

    }
