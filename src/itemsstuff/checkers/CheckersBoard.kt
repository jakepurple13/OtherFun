package itemsstuff.checkers

class CheckersBoard {
    private val board: Array<Array<Cell>> = Array(8) { x -> Array(8) { y -> Cell(x, y, if ((x + y) % 2 == 0) Color.WHITE else Color.BLACK) } }

    val publicBoard: Array<Array<PublicCell>> get() = board.toPublicCell()

    init {
        for (i in 0..2 step 2) {
            for (j in 0 until 8 step 2) {
                board[i][j].piece = Piece(Color.WHITE)
            }
        }
        for (j in 1 until 8 step 2) {
            board[1][j].piece = Piece(Color.WHITE)
        }
        for (i in 5..7 step 2) {
            for (j in 0 until 8 step 2) {
                board[i][j].piece = Piece(Color.BLACK)
            }
        }
        for (j in 1 until 8 step 2) {
            board[6][j].piece = Piece(Color.BLACK)
        }
    }

    private fun Array<Array<Cell>>.toPublicCell() =
        map { columns -> columns.map { it.toPublicCell() }.toTypedArray() }.toTypedArray()

    fun move(fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
        val fromCell = board[fromX][fromY]
        val toCell = board[toX][toY]

        return fromCell.piece?.move(fromCell, toCell) == true
    }
}

enum class Color {
    BLACK, WHITE
}

data class Piece(val color: Color) {
    fun move(fromCell: Cell, toCell: Cell): Boolean {
        val (fromRow, fromCol) = fromCell
        val (rowTo, columnTo) = toCell
        var moves = false
        if (toCell.isEmpty()) {
            if ((fromRow + 1 == rowTo || fromCol + 1 == columnTo) && (fromRow - 1 == rowTo || fromCol - 1 == columnTo)) {
                moves = true
            } else if ((fromRow + 1 == rowTo || fromCol - 1 == columnTo) && (fromRow - 1 == rowTo || fromCol + 1 == columnTo)) {
                moves = true
            }
        } else if (!toCell.isEmpty()) {
            if ((fromRow + 2 == rowTo || fromCol + 2 == columnTo) && (fromRow - 2 == rowTo || fromCol - 2 == columnTo)) {
                moves = true
            } else if ((fromRow + 2 == rowTo || fromCol - 2 == columnTo) && (fromRow - 2 == rowTo || fromCol + 2 == columnTo)) {
                moves = true
            }
        }
        return moves
    }

}

data class Cell(val x: Int, val y: Int, val color: Color, var piece: Piece? = null) {
    fun isEmpty() = piece == null
    fun toPublicCell() = PublicCell(x, y, piece)
    infix operator fun contains(piece: Piece) = this.piece == piece
}

data class PublicCell(val x: Int, val y: Int, val piece: Piece?)