package com.repository;

import com.model.CartItem;
import com.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class CartRepo{
    private final JdbcTemplate jdbcTemplate;
    private User user;

    public CartRepo(JdbcTemplate jdbcTemplate, User user){
        this.jdbcTemplate = jdbcTemplate;
        this.user = user;
    }

    public void addUser(User user) {
        String sql = "insert into user (name, user_membership) values (?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getUserMembership().toString());
    }

    public void addItem(CartItem cartItem) {
        String sql = "insert into cartitem (name, price, qty, User_id) values (?,?,?,?)";
        jdbcTemplate.update(sql, cartItem.getName(), cartItem.getPrice(), cartItem.getQty(), cartItem.getUser().getId());
    }

    public RowMapper<CartItem> getRows(){
        return new RowMapper<CartItem>() {
            @Override
            public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                user.setId(rs.getInt("User_id"));
                return new CartItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        BigDecimal.valueOf(rs.getInt("price")),
                        rs.getInt("qty"),
                        user
                );
            }
        };
    }

    public List<CartItem> fetchAllItems() {
        String sql = "select * from cartitem";
        return jdbcTemplate.query(sql, getRows());
    }

    public List<CartItem> fetchByUserName(String name) {
        String sql = "select c.id, c.name, c.price, c.qty, c.User_id" +
                    " from cartitem c" +
                    " join user u on c.User_id = u.id" +
                    " where u.name = ? ";
        return jdbcTemplate.query(sql, getRows(), name);
    }

    public void deleteById(int id) {
        String sql = "delete from cartitem where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
