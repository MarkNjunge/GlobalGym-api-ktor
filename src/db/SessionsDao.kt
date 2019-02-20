package com.marknjunge.db

import com.marknjunge.model.Session
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface SessionsDao {
    @SqlUpdate(
        """
        CREATE TABLE IF NOT EXISTS workout_sessions
          (
             id           VARCHAR(50) PRIMARY KEY NOT NULL,
             name         VARCHAR(100) NOT NULL,
             user_id      VARCHAR(255) REFERENCES users(id) NOT NULL,
             session_date BIGINT NOT NULL,
             gym          VARCHAR(255) REFERENCES gyms(id) NOT NULL,
             completed    BOOLEAN DEFAULT false NOT NULL,
             steps        JSON NOT NULL
          )
    """
    )
    fun createTable()

    @SqlUpdate(
        """
        INSERT INTO workout_sessions
            (id,
             name,
             user_id,
             session_date,
             gym,
             steps)
        VALUES      ( :session.id,
                      :session.name,
                      :session.user,
                      :session.date,
                      :session.gym,
                      :session.steps)
    """
    )
    fun insert(session: Session)

    @SqlUpdate(
        """
        UPDATE workout_sessions
        SET    name = :session.name,
               session_date = :session.date,
               gym = :session.gym,
               steps = :session.steps
        WHERE  id = :session.id
    """
    )
    fun update(session: Session)

    @SqlQuery("SELECT * from workout_sessions WHERE user_id=:userId")
    fun selectForUser(userId: String): List<Session>

    @SqlUpdate("DELETE FROM workout_sessions WHERE id = :sessionId")
    fun deleteSession(sessionId: String)
}