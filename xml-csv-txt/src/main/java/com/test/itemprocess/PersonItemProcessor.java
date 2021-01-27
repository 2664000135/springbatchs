package com.test.itemprocess;

import com.test.domain.Person;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

/**
 * @ClassName PersonItemProcessor
 * @Description ItemProcessor主要负责数据的转换与处理，将读取到的文件 转换为输出文件的对象，所以temProcessor<Person, Person>
 *     这里不一定都是Person,实现process方法，实现数据的转换与处理。
 * @Author QiaoFoPing
 * @Date 2021/1/20 15:53
 * @Version 1.0
 */
@Service
public class PersonItemProcessor implements ItemProcessor<Person,Person> {
    @Override
    public Person process(Person person) throws Exception {
        person.setAge(person.getAge() + 1);
        person.setName(person.getName() + "-_-");
        return person;
    }
}