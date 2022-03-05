package guides.hazelcast.springboot;

import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.concurrent.ConcurrentMap;

@RestController
public class CommandController {
    @Autowired
    private HazelcastInstance hazelcastInstance;

    private ConcurrentMap<String,String> retrieveMap() {
        return hazelcastInstance.getMap("cache10sec");
    }

    private ConcurrentMap<String,String> retrieveMapPers() {
        return hazelcastInstance.getMap("cachePersistent");
    }

    @PostMapping("/put")
    public CommandResponse put(@RequestParam(value = "key") String key, @RequestParam(value = "value") String value) {

        System.out.println(MessageFormat.format("new data came key {0}, value {1}", key, value));

        retrieveMap().put(key, value);
        return new CommandResponse(value);
    }

    @PostMapping("/put2")
    public CommandResponse put2(@RequestParam(value = "key") String key, @RequestParam(value = "value") String value) {

        System.out.println(MessageFormat.format("new data came key {0}, value {1}", key, value));

        retrieveMapPers().put(key, value);
        return new CommandResponse(value);
    }

    @GetMapping("/get")
    public CommandResponse get(@RequestParam(value = "key") String key) {
        String value = retrieveMap().get(key);

        System.out.println(MessageFormat.format("get key {0}", key));

        return new CommandResponse(value);
    }

    @GetMapping("/get2")
    public CommandResponse get2(@RequestParam(value = "key") String key) {
        String value = retrieveMapPers().get(key);

        System.out.println(MessageFormat.format("get key {0}", key));

        return new CommandResponse(value);
    }

    @GetMapping("/size")
    public CommandResponse getSize() {
        System.out.println("size");

        return new CommandResponse(String.valueOf(retrieveMap().size()));
    }

    @GetMapping("/size2")
    public CommandResponse getSize2() {
        System.out.println("size");

        return new CommandResponse(String.valueOf(retrieveMapPers().size()));
    }

    @GetMapping("/health")
    public CommandResponse getHealth() {
        System.out.println("health");

        return new CommandResponse("true");
    }

}
