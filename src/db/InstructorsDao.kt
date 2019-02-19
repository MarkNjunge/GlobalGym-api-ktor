package com.marknjunge.db

import com.marknjunge.model.Gym
import com.marknjunge.model.Instructor
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import org.jdbi.v3.sqlobject.SingleValue
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.jdbi.v3.sqlobject.statement.UseRowMapper
import java.sql.ResultSet

interface InstructorsDao{
    @SqlUpdate(
        """
    CREATE TABLE IF NOT EXISTS instructors
        (
          id            VARCHAR(255) NOT NULL PRIMARY KEY UNIQUE,
          first_name    VARCHAR(255) NOT NULL,
          last_name     VARCHAR(255) NOT NULL,
          email         VARCHAR(255) NOT NULL UNIQUE,
          photo         VARCHAR(255) NOT NULL,
          year_of_birth INT          NOT NULL,
          gender        CHAR         NOT NULL,
          country       VARCHAR(255) NOT NULL,
          gym VARCHAR(255) REFERENCES gyms (id)
        )
    """
    )
    fun createTable()

    @SqlUpdate(
        """
        INSERT INTO instructors(id, first_name, last_name, email, photo, year_of_birth, gender, country)
        VALUES (:instructor.id, :instructor.firstName, :instructor.lastName, :instructor.email, :instructor.photo, :instructor.yearOfBirth, :instructor.gender, :instructor.country)
    """
    )
    fun insert(instructor: Instructor)

    @SqlUpdate(
        """
        UPDATE instructors
            SET
              first_name    = :instructor.firstName,
              last_name     = :instructor.lastName,
              email         = :instructor.email,
              photo         = :instructor.photo,
              year_of_birth = :instructor.yearOfBirth,
              gender        = :instructor.gender,
              country       = :instructor.country
            WHERE id = :instructor.id
    """
    )
    fun update(instructor: Instructor)

    @SqlQuery(
        """
    SELECT instructors.id AS i_id,
       instructors.first_name,
       instructors.last_name,
       instructors.email,
       instructors.photo,
       instructors.year_of_birth,
       instructors.gender,
       instructors.country AS i_country,
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
    FROM   instructors
           LEFT JOIN gyms
                  ON instructors.gym = gyms.id;
    """
    )
    @UseRowMapper(InstructorGymMapper::class)
    fun selectAll(): List<Instructor>

    @SqlQuery(
        """
    SELECT instructors.id AS i_id,
       instructors.first_name,
       instructors.last_name,
       instructors.email,
       instructors.photo,
       instructors.year_of_birth,
       instructors.gender,
       instructors.country AS i_country,
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
    FROM   instructors
           LEFT JOIN gyms
                  ON instructors.gym = gyms.id
    WHERE instructors.id=:instructorId
    """
    )
    @SingleValue
    @UseRowMapper(InstructorGymMapper::class)
    fun selectById(instructorId: String): Instructor?

    @SqlUpdate("UPDATE instructors SET gym = :gymId WHERE instructors.id=:instructorId")
    fun setGym(instructorId: String, gymId: String)

    @SqlUpdate("UPDATE instructors SET gym = NULL WHERE instructors.id=:instructorId")
    fun removeGym(instructorId: String)

    class InstructorGymMapper : RowMapper<Instructor> {
        override fun map(rs: ResultSet, ctx: StatementContext): Instructor {
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

            return Instructor(
                rs.getString("i_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("photo"),
                rs.getInt("year_of_birth"),
                rs.getString("gender").toCharArray().first(),
                rs.getString("i_country"),
                gym
            )
        }
    }
}