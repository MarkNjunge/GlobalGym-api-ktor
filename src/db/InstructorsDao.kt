package com.marknjunge.db

import com.marknjunge.model.Instructor
import org.jdbi.v3.sqlobject.SingleValue
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface InstructorsDao {
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

    @SqlQuery("SELECT * FROM instructors")
    fun selectAll(): List<Instructor>

    @SqlQuery("SELECT * FROM instructors WHERE instructors.id=:instructorId")
    @SingleValue
    fun selectById(instructorId: String): Instructor?

    @SqlUpdate("UPDATE instructors SET gym = :gymId WHERE instructors.id=:instructorId")
    fun setGym(instructorId: String, gymId: String)

    @SqlUpdate("UPDATE instructors SET gym = NULL WHERE instructors.id=:instructorId")
    fun removeGym(instructorId: String)
}