package ifsul.agileproject.rachadinha.domain.entity;

/**
 * Represents the status of a racha.
 * The possible values are OPEN, CLOSED, and FINISHED.
 * OPEN means that the racha is still open for new participants.
 * CLOSED means that the racha is closed for new participants.
 * FINISHED means that the racha is finished and the goal was reached.
 */
public enum Status {
    OPEN, CLOSED, FINISHED
}
