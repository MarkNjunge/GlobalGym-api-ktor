package com.marknjunge.db

import com.marknjunge.model.Gym
import model.GymImage
import org.jdbi.v3.sqlobject.SingleValue
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface GymDao {
    @SqlUpdate(
        """
        CREATE TABLE IF NOT EXISTS gyms
            (
            id         VARCHAR(255) NOT NULL PRIMARY KEY UNIQUE,
            name       VARCHAR(255) NOT NULL,
            logo       VARCHAR(255) NOT NULL,
            phone      VARCHAR(255) NOT NULL,
            website    VARCHAR(255) NOT NULL,
            open_time  INT          NOT NULL,
            close_time INT          NOT NULL,
            available  BOOLEAN,
            country    VARCHAR(255) NOT NULL,
            city       VARCHAR(255) NOT NULL,
            cords_lat  REAL         NOT NULL,
            cords_lng  REAL         NOT NULL
            )
    """
    )
    fun createTable()

    @SqlUpdate(
        """
        INSERT INTO gyms(id, name, logo, phone, website, open_time, close_time, available, country, city, cords_lat, cords_lng)
        VALUES (:gym.id, :gym.name, :gym.logo, :gym.phone, :gym.website, :gym.openTime, :gym.closeTime, :gym.available, :gym.country, :gym.city, :gym.cordsLat, :gym.cordsLng)
    """
    )
    fun insert(gym: Gym)

    @SqlQuery("SELECT * FROM gyms")
    fun selectAll(): List<Gym>

    @SqlQuery("SELECT * FROM gyms WHERE gyms.id = :gymId")
    @SingleValue
    fun selectById(gymId: String): Gym?

    @SqlQuery("SELECT * FROM gyms WHERE gyms.name ILIKE :name")
    fun searchByName(name: String): List<Gym>

    @SqlUpdate("""
        UPDATE gyms
        SET name       = :gym.name,
            logo       = :gym.logo,
            phone      = :gym.phone,
            website    = :gym.website,
            open_time  = :gym.openTime,
            close_time = :gym.closeTime,
            available  = :gym.available,
            country    = :gym.country,
            city       = :gym.city,
            cords_lat  = :gym.cordsLat,
            cords_lng  = :gym.cordsLng
        WHERE gyms.id = :gym.id
    """)
    fun update(gym: Gym)

    @SqlUpdate(
        """
        CREATE TABLE IF NOT EXISTS gym_images(
          id SERIAL NOT NULL PRIMARY KEY,
          gym_id VARCHAR(255) REFERENCES gyms(id) NOT NULL,
          url VARCHAR(255) NOT NULL
        )
    """
    )
    fun createImagesTable()

    @SqlUpdate("INSERT INTO gym_images(gym_id, url) VALUES (:gymId, :url)")
    fun insertImage(gymId: String, url: String)

    @SqlQuery("SELECT * FROM gym_images")
    fun selectAllImages(): List<GymImage>

    @SqlQuery("SELECT url FROM gym_images WHERE gym_images.gym_id=:gymId")
    fun selectImagesForGym(gymId: String): List<String>

    @SqlUpdate("DELETE FROM gym_images WHERE gym_id=:gymId AND url=:url")
    fun removeImage(gymId: String, url: String)
}