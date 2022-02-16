package fr.scriptis.simplechess.entities.chess;

import fr.scriptis.simplechess.entities.Entity;
import fr.scriptis.simplechess.utils.AssetsManager;

public abstract class Piece extends Entity {

    public abstract int[][] getPossibleMoves();
}
