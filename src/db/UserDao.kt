package com.marknjunge.db

import com.marknjunge.model.Gym
import com.marknjunge.model.User
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import org.jdbi.v3.sqlobject.SingleValue
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.jdbi.v3.sqlobject.statement.UseRowMapper
import java.sql.ResultSet

interface UserDao {
    @SqlUpdate(
        """
    CREATE TABLE IF NOT EXISTS users
        (
          id            VARCHAR(255) NOT NULL PRIMARY KEY UNIQUE,
          first_name    VARCHAR(255) NOT NULL,
          last_name     VARCHAR(255) NOT NULL,
          email         VARCHAR(255) NOT NULL UNIQUE,
          photo         VARCHAR(255) NOT NULL,
          year_of_birth INT          NOT NULL,
          gender        CHAR         NOT NULL,
          country       VARCHAR(255) NOT NULL,
          weight        INT          NOT NULL,
          target_weight INT          NOT NULL,
          preferred_gym VARCHAR(255) REFERENCES gyms (id)
        )
    """
    )
    fun createTable()

    @SqlUpdate(
        """
        INSERT INTO users(id, first_name, last_name, email, photo, year_of_birth, gender, country, weight, target_weight)
        VALUES (:user.id, :user.firstName, :user.lastName, :user.email, :user.photo, :user.yearOfBirth, :user.gender, :user.country, :user.weight, :user.targetWeight)
    """
    )
    fun insert(user: User)

    @SqlUpdate(
        """
        UPDATE users
            SET
              first_name    = :user.firstName,
              last_name     = :user.lastName,
              email         = :user.email,
              photo         = :user.photo,
              year_of_birth = :user.yearOfBirth,
              gender        = :user.gender,
              country       = :user.country,
              weight        = :user.weight,
              target_weight =:user.targetWeight
            WHERE id = :user.id
    """
    )
    fun update(user: User)

    @SqlQuery(
        """
    SELECT users.id AS u_id,
       users.first_name,
       users.last_name,
       users.email,
       users.photo,
       users.year_of_birth,
       users.gender,
       users.country AS u_country,
       users.weight,
       users.target_weight,
       gyms.id  AS g_id,
       gyms.name,
       gyms.logo,
       gyms.phone,
       gyms.website,
       gyms.open_time,
       gyms.close_time,
       gyms.available,
       gyms.country AS g_country,
       gyms.city,
       gyms.cords_lat,
       gyms.cords_lng
    FROM   users
           LEFT JOIN gyms
                  ON users.preferred_gym = gyms.id;
    """
    )
    @UseRowMapper(UserGymMapper::class)
    fun selectAll(): List<User>

    @SqlQuery(
        """
    SELECT users.id AS u_id,
       users.first_name,
       users.last_name,
       users.email,
       users.photo,
       users.year_of_birth,
       users.gender,
       users.country AS u_country,
       users.weight,
       users.target_weight,
       gyms.id  AS g_id,
       gyms.name,
       gyms.logo,
       gyms.phone,
       gyms.website,
       gyms.open_time,
       gyms.close_time,
       gyms.available,
       gyms.country AS g_country,
       gyms.city,
       gyms.cords_lat,
       gyms.cords_lng
    FROM   users
           LEFT JOIN gyms
                  ON users.preferred_gym = gyms.id
    WHERE users.id=:userId
    """
    )
    @SingleValue
    @UseRowMapper(UserGymMapper::class)
    fun selectById(userId: String): User?

    @SqlUpdate("UPDATE users SET preferred_gym = :gymId WHERE users.id=:userId")
    fun setPreferredGym(userId: String, gymId: String)

    @SqlUpdate("UPDATE users SET preferred_gym = NULL WHERE users.id=:userId")
    fun removePreferredGym(userId: String)

    class UserGymMapper : RowMapper<User> {
        override fun map(rs: ResultSet, ctx: StatementContext): User {
            val gym = if (rs.getString("g_id") == null) {
                null
            } else {
                Gym(
                    rs.getString("g_id"),
                    rs.getString("name"),
                    rs.getString("logo"),
                    rs.getString("phone"),
                    rs.getString("website"),
                    rs.getInt("open_time"),
                    rs.getInt("close_time"),
                    rs.getBoolean("available"),
                    rs.getString("g_country"),
                    rs.getString("city"),
                    rs.getFloat("cords_lat"),
                    rs.getFloat("cords_lng")
                )
            }

            return User(
                rs.getString("u_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("photo"),
                rs.getInt("year_of_birth"),
                rs.getString("gender").toCharArray().first(),
                rs.getString("u_country"),
                rs.getInt("weight"),
                rs.getInt("target_weight"),
                gym
            )
        }
    }
}