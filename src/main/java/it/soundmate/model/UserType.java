/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 12/12/20, 14:41
 */

package it.soundmate.model;

public enum UserType {
    BAND {
        @Override
        public String toString() {
            return "BAND";
        }
    },
    BAND_MANAGER {
        @Override
        public String toString() {
            return "BAND_MANAGER";
        }
    },
    ROOM_RENTER {
        @Override
        public String toString() {
            return "ROOM_RENTER";
        }
    },
    SOLO {
        @Override
        public String toString() {
            return "SOLO";
        }
    }
}
