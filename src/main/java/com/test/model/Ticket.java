package com.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The Ticket class represents database entity
 */

@Data
@AllArgsConstructor
public class Ticket {

    /**
     * An integer value to keep database ID
     */
    private Integer id;
}
