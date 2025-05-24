package rest_assured.models;

import lombok.Data;

@Data
public class UpdateUserResponse {
    String name, job, updatedAt;
}
