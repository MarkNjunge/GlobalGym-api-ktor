package com.marknjunge.db

import com.marknjunge.model.User
import org.jdbi.v3.sqlobject.SingleValue
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

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

    @SqlUpdate("""
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
    """)
    fun update(user:User)

    @SqlQuery("SELECT * FROM users")
    fun selectAll(): List<User>

    @SqlQuery("SELECT * FROM users WHERE id=:userId")
    @SingleValue
    fun selectById(userId: String): User?
}