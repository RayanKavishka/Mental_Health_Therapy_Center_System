package lk.ijse.mental_health_therapy_center_system.bo.custom.impl;

import lk.ijse.mental_health_therapy_center_system.bo.custom.UserBO;
import lk.ijse.mental_health_therapy_center_system.dao.DAOFactory;
import lk.ijse.mental_health_therapy_center_system.dao.custom.UserDAO;
import lk.ijse.mental_health_therapy_center_system.dto.UserDTO;
import lk.ijse.mental_health_therapy_center_system.entity.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserBOImpl implements UserBO {
    private final UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public boolean saveUser(UserDTO userDTO) {
        try {
            User user = new User();
            user.setUserName(userDTO.getUserName());

            String encryptedPassword = BCrypt.hashpw(
                    userDTO.getPassword(),
                    BCrypt.gensalt(12)
            );
            user.setPassword(encryptedPassword);

            user.setRole(userDTO.getRole());
            return userDAO.save(user);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkCredentials(UserDTO userDTO) {
        try {
            User user = userDAO.getUser(userDTO.getUserName());
            if (user == null) {
                return false;

            }

            boolean isMatched = false;
            if (userDTO.getRole().equals(user.getRole())) {
                if (BCrypt.checkpw(
                    userDTO.getPassword(), user.getPassword()
                )) {
                    isMatched = true;
                    return isMatched;

                } else {
                    return false;
                }

            } else {
                return false;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
