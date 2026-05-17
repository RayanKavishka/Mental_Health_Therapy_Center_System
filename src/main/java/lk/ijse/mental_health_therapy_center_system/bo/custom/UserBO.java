package lk.ijse.mental_health_therapy_center_system.bo.custom;

import lk.ijse.mental_health_therapy_center_system.bo.SuperBO;
import lk.ijse.mental_health_therapy_center_system.dto.UserDTO;

public interface UserBO extends SuperBO {
    boolean saveUser(UserDTO userDTO);
    boolean checkCredentials(UserDTO userDTO);
}
