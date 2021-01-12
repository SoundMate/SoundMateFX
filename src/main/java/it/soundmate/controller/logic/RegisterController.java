/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 12/01/21, 15:22
 * Last edited: 11/01/21, 20:53
 */

package it.soundmate.controller.logic;


import it.soundmate.database.Connector;
import it.soundmate.model.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterController {


//    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);
//
//    private final Connector connector;
//    private String email;
//    private String password;
//    private UserType userType;
//    private static final String ACC_BANNED_ERR = "\t ***** THIS ACCOUNT HAS BEEN BANNED *****";
//    private static final String EMAIL_EXISTS_ERR = "\t ***** THIS EMAIL ALREADY EXISTS *****";
//    private static final String ERR_INSERT = "Error inserting user";
//

//    public RegisterController(Connector connector) {
//        this.connector = connector;
//    }
//
//

//    public int registration(String email, String password, UserType userType){
//
//        ResultSet resultSet;
//        int userID = 0;
//        if (dbServices.checkIfBanned(email)){
//            log.error(ACC_BANNED_ERR);
//            return -1;
//        }else if (dbServices.checkEmailBoolean(email)){
//            log.error(EMAIL_EXISTS_ERR);
//            return -2;
//        }
//        else {
//
//            String sql = "INSERT INTO registered_users (email, password, user_type) VALUES (?, ?, ?)";
//
//            try (Connection conn = connector.getConnection();
//                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//
//                pstmt.setString(1, email);
//                pstmt.setString(2, password);
//                pstmt.setString(3, userType.toString());
//
//
//                int rowAffected = pstmt.executeUpdate();
//                if (rowAffected == 1) {
//
//                    resultSet = pstmt.getGeneratedKeys();
//                    if (resultSet.next())
//                        userID = resultSet.getInt(1);
//                }
//            } catch (SQLException ex) {
//                throw new RepositoryException(ERR_INSERT, ex);
//            } return userID;
//        }
//    }






//    public int regiBandManController(BandManBean bandManBean){
//
//        ResultSet resultSet;
//
//        int userID = 0;
//        if (dbServices.checkIfBanned(bandManBean.getEmail())){
//            log.error(ACC_BANNED_ERR);
//            return -1;
//        }else if (dbServices.checkEmailBoolean(bandManBean.getEmail())){
//            log.error(EMAIL_EXISTS_ERR);
//            return -2;
//        } else {
//            String sql = " WITH ins1 AS (\n" +
//                    "     INSERT INTO registered_users (email, password, user_type)\n" +
//                    "         VALUES (?, ?, ?)\n" +
//                    " -- ON     CONFLICT DO NOTHING         -- optional addition in Postgres 9.5+\n" +
//                    "         RETURNING id AS sample_id\n" +
//                    " ), ins2 AS (\n" +
//                    "     INSERT INTO users (id, encoded_profile_img)\n" +
//                    "         SELECT sample_id, ? FROM ins1\n" +
//                    " )\n" +
//                    "INSERT INTO solo (id, first_name, last_name)\n" +
//                    "SELECT sample_id, ?, ? FROM ins1;";
//
//            try (Connection conn = connector.getConnection();
//                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//
//                pstmt.setString(1, bandManBean.getEmail());
//                pstmt.setString(2, bandManBean.getPassword());
//                pstmt.setString(3, bandManBean.getUserType().toString());
//                pstmt.setString(4, "bmIMG");
//                pstmt.setString(5, bandManBean.getFirstName());
//                pstmt.setString(6, bandManBean.getLastName());
//
//
//                int rowAffected = pstmt.executeUpdate();
//                if (rowAffected == 1) {
//
//                    resultSet = pstmt.getGeneratedKeys();
//                    if (resultSet.next())
//                        userID = resultSet.getInt(1);
//                }
//            } catch (SQLException ex) {
//                throw new RepositoryException(ERR_INSERT, ex);
//            } return userID;
//        }
//    }

}


