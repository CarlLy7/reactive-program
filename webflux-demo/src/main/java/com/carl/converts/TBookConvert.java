package com.carl.converts;

import com.carl.model.TBook;
import com.carl.model.TUser;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

/**
 * @description: 自定义类型转换器, 实现通过Row中的数据转成TBook对象
 * @author: carl
 * @date: 2026.01.09
 * @Since: 1.0
 */
@ReadingConverter //读取数据的时候可以进行转换
//@WritingConverter //输出的时候可以转换
public class TBookConvert implements Converter<Row, TBook> {

    @Override
    public TBook convert(Row source) {
        TBook tBook = new TBook();
        tBook.setId(source.get("id", Integer.class));
        tBook.setTitle(source.get("title", String.class));
        TUser tUser = new TUser();
        tUser.setId(source.get("author_id", Integer.class));
        tUser.setName(source.get("authorName", String.class));
        tBook.setUser(tUser);

        return tBook;
    }
}
