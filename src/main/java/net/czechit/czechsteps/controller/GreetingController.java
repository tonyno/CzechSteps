package net.czechit.czechsteps.controller;

// https://spring.io/guides/gs/rest-service/

// gcloud beta emulators datastore start

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import com.google.cloud.datastore.*;
import net.czechit.czechsteps.objects.Person;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    // Create an authorized Datastore service using Application Default Credentials.
    private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    // Create a Key factory to construct keys associated with this project.
    private final KeyFactory keyFactory = datastore.newKeyFactory().setKind("Person");


    @ResponseBody
    @RequestMapping("/greetingRest")
    public List<Person> greeting(@RequestParam(value="name", defaultValue="World") String name)
    {

        // https://cloud.google.com/datastore/docs/concepts/entities?hl=en_US&_ga=2.176856998.-2057309974.1512591011&_gac=1.14264645.1512644561.CjwKCAiA6qPRBRAkEiwAGw4SdoVBu9FZebJmjHav91KRlpO-WHOEXC26Hy-TGZwJkHITFipIFj6Z5RoCgBIQAvD_BwE

        // https://cloud.google.com/datastore/docs/concepts/queries

        Entity newPerson = Entity.newBuilder(keyFactory.newKey("ext1111"))
                .set("Name", "Antonín Kmoch")
                .build();
        datastore.put(newPerson);

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Person")
                .build();
        QueryResults<Entity> tasks = datastore.run(query);
        List<Person> persons = new ArrayList<Person>();
        while (tasks.hasNext())
        {
            Entity myTask = tasks.next();
            persons.add(new Person(myTask));
        }
        return persons;
    }
}