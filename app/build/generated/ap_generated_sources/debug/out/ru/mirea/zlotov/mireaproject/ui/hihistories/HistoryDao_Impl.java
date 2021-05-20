package ru.mirea.zlotov.mireaproject.ui.hihistories;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ru.mirea.zlotov.mireaproject.ui.histories.Cell;

@SuppressWarnings({"unchecked", "deprecation"})
public final class HistoryDao_Impl implements HistoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Cell> __insertionAdapterOfCell;

  private final EntityDeletionOrUpdateAdapter<Cell> __deletionAdapterOfCell;

  private final EntityDeletionOrUpdateAdapter<Cell> __updateAdapterOfCell;

  public HistoryDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCell = new EntityInsertionAdapter<Cell>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Cell` (`id`,`text`) VALUES (nullif(?, 0),?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Cell value) {
        stmt.bindLong(1, value.id);
        if (value.text == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.text);
        }
      }
    };
    this.__deletionAdapterOfCell = new EntityDeletionOrUpdateAdapter<Cell>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Cell` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Cell value) {
        stmt.bindLong(1, value.id);
      }
    };
    this.__updateAdapterOfCell = new EntityDeletionOrUpdateAdapter<Cell>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Cell` SET `id` = ?,`text` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Cell value) {
        stmt.bindLong(1, value.id);
        if (value.text == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.text);
        }
        stmt.bindLong(3, value.id);
      }
    };
  }

  @Override
  public void insert(final Cell cell) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCell.insert(cell);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Cell cell) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCell.handle(cell);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Cell cell) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCell.handle(cell);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Cell> getAll() {
    final String _sql = "SELECT * FROM Cell";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
      final List<Cell> _result = new ArrayList<Cell>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Cell _item;
        _item = new Cell();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfText)) {
          _item.text = null;
        } else {
          _item.text = _cursor.getString(_cursorIndexOfText);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Cell getById(final long id) {
    final String _sql = "SELECT * FROM Cell WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
      final Cell _result;
      if(_cursor.moveToFirst()) {
        _result = new Cell();
        _result.id = _cursor.getLong(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfText)) {
          _result.text = null;
        } else {
          _result.text = _cursor.getString(_cursorIndexOfText);
        }
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
