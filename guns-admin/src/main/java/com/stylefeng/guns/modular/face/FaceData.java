package com.stylefeng.guns.modular.face;

import java.util.List;

public class FaceData {
    private String face_token;
    private List<FaceUser> user_list;

    public String getFace_token() {
        return face_token;
    }

    public void setFace_token(String face_token) {
        this.face_token = face_token;
    }

    public List<FaceUser> getUser_list() {
        return user_list;
    }

    public void setUser_list(List<FaceUser> user_list) {
        this.user_list = user_list;
    }
}
