package com.merrill.userrest;

import lombok.Data;

@Data
public class ProjectMembership {
    private String id;
    private String projectId;
    private String userId;
}
