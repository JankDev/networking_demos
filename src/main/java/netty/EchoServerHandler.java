package netty;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import io.netty.buffer.UnpooledHeapByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static io.netty.buffer.Unpooled.EMPTY_BUFFER;
import static io.netty.channel.ChannelHandler.Sharable;

@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    static class User {
        String firstName;
        String lastName;

        public User() {
        }

        public User(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public User setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public String getLastName() {
            return lastName;
        }

        public User setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        List<User> users = new ArrayList<>();

        users.add(new User("Robert", "Kraut"));

        Gson gson = new Gson();

        JsonArray arr = gson.toJsonTree(users).getAsJsonArray();


        if (msg instanceof UnpooledHeapByteBuf) {
            final String HTTP_RESPONSE = "HTTP/1.1 200 OK\n" +
                    "Content-Type: application/json\n" +
                    "Content-Length: %d\n" +
                    "\n" +
                    "%s";

            String message = String.format(HTTP_RESPONSE, arr.toString().length(), arr.toString());
            System.out.println(HTTP_RESPONSE);
            UnpooledHeapByteBuf buf = (UnpooledHeapByteBuf) msg;
            buf.clear();
            buf.capacity(message.getBytes().length + 1);
            ((UnpooledHeapByteBuf) msg).writeBytes(message.getBytes());
        }

        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();

        ctx.close();
    }

    public static void main(String[] args) {
        int[] arr = {1, 5, 3, 2, 5, 6, 7, 2, 7, 4};

        Arrays.sort(arr);
        for (int n :
                arr) {
            System.out.print(n + " ");
        }
        Random random = ThreadLocalRandom.current();
        System.out.println();
        for (int i = 0; i < arr.length; i++) {
            int temp = arr[i];
            int rand = random.nextInt(arr.length);
            arr[i] = arr[rand];
            arr[rand] = temp;
        }

        for (int n :
                arr) {
            System.out.print(n + " ");
        }
    }
}
