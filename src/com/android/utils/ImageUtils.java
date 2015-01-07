package com.android.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Bitmap, byte array, Drawable之间的转换
 *
 * @author cyc
 */
public class ImageUtils {

    private static String delS = "89504e470d0a1a0a0000000d4948445200000030000000340806000000cc93bb910000000473424954080808087c08648800000efd494441546881ad5ad9721c57723d99796bed1d8d261612a0444a224d5022a9f11272c438bc4c38c2110abff117f41df81f3eebcdb63ce35d1a8e3022c0450b248a2045107ba3babbaaabeaa61fba0ad300b151331951d1d5dbad3c27f3e63d95b7083fc3eedebd2b972e5d72a7a6a63a22f28eaa5e13911b00e6014c105155555d22324424aa9a0318008854754f55d7acb50f013cc9f3fc5b665e1f0c0683c5c5c5ec4d7da1f3fe7071719101f86118d655b525226d00d344748988e601bc05e002803a8080880c00298e1cc010c0405523001baafa94887e24a2b52ccbd6016c5a6bb70783c1eecacacae0debd7b1680fe490014ce1bdff7a71dc7b9a9aa3789e87d229a23a21a802a800a1179aaea109100202222552d5ed41251aeaa19804455fb007a1845e527557d64ad5d56d52ffbfdfef3959595e4debd7bf91f05e0eeddbbb2b0b0e00541d014918baafa2e33df22a29b44b44044d3659a10fd61a8f1f37153d543e7aa6a55d502d854d5af013c00f03b8c526b2dcff3edb352cb9c06606161c1f37d7f92886e32f3af007cc0cc5300da00eacc6c786420a243c769005415d65aa82ae579ceaada04f067aa3a0de096aa2eabea67799e7f1504c17300fb27f978ec953ef9e413677676b61204c18ce338d701fc25805f31f37bcc1c1091533a2a22380ae0d0058af747d9b7d61e3a8a88e4d6daa1b5765555ff3dcff3cf017c9565d9b3e170b8bfb8b8383ceaebb111989d9dad8461f896887ca8aa7fcfcc1f10d12c3307323230f321c78f3afc1a53639fab2a98b9747a1c08e779ee12d1256bed3f12d1acb5b6e138ce6f017c0d60ebd40894395fad56df52d58f98f9af99f997cc7c99998d88f0b8f3cc7caad367d9d194caf31c799ec35aab799eabb5f699aafe8fb5f6bf45e43ff6f6f65601f4c7e7c4a10894390fe0ba31e61f00fc85885c1011e3380e958cff314e9f64e5d822026b2dd234a52ccb26adb51f31b3a7aadd6ab53a8ca2680d40b7fc9f94278b8b8b1c04c1a431e6fd82f9bf11917744c42b9827630cc68ff134781350e3bf67e683f144a41c83465f9383d1bae2001812510c60e7ead5abd1fdfbf7f5004059e7c3307c8788fe9988fed618336f8c095dd7251121668688c0f33c789e07d775c1cc07f9fb2600c659771c079ee7c1f77d1863ca141a9f6304c0a86a4b558de3383fd6ebf5ad2b57aee4f7efdfb7650af9beef4f5a6bdf15910f44e43d634c5896c832ef832040a3d140100420222449829d9d1df4fbfd834a721a90f1ef8908beefa35eaf230802b8ae8b24494044e8f57ab0d622cb32121160b4505e01b067adbdeebaeec6cd9b379f01480d0084615817919bcc7c8b99678c31a1889832a4cc0ccff3d068343037378746a3012242b7db3d9880711c23cfcf5c380f00186350abd53037378766b309630ca2283a00daeff70fe60411b1aabaaada51d5db799eefa569da05d095c5c54536c65c22a25f8ac85f398e7345449ac6181a9f589ee7a1d96c6633333371bbdd8e2b954a2c2259b1107159418e327df47d4946b3d944a7d3c967666686c5787d554d7bbd1e0f060349d314d6daf139c18524618c84e10f1f7ffcf18e01608c312d227a9f991744a4618c79adb617ef87aaba8d9186c9c2300c2e5dba74c1f7fd2a80332351325fafd7313737874ea793d66ab5ae3166bf18d30330cbcccef8f5cb894e44d53ccfdfb3d66e5a6ba79324796e8c311d63cc14115d129129638c192f97a5635996a1d7ebe59b9b9b3d55fda9d168bcf07d3fa8d56a3701ccc471ec0330bbbbbbc7ce092282e779a8d7ebe8743a79a7d3499bcde68631e6499aa67bdd6ed7dfd8d8e84451d429d91fb7a2e279cc3c2922b344343b1c0e7f32aeebbea3aa73c6989a881822e2a393d05a8bc16080e170a8bbbbbb36088297b3b3b3ff76e3c60d715d176118ca69913885f927d6da4f9f3e7d1a7df7dd77bfd8dfdf6f665966cb4a74d48a4810808aaace11d1be61e66bcc3ccfcc556696e3f48caa22cf73244922c3e130c8f33c78fefcb92449b2b7b0b0b05cad56715c240683015415aeeb1ecbfc70387cb0bababafef9e79fbb511485006a9ee799f1ca77c40f2a4084d6da79c771ac61e61bcc7c51442a630bc9b1a6aa9eaa7600dc180e87f6d1a347cb0056eedcb9b3765c240020cbb213995f5d5d5dfff2cb2fe7a328ba6eadbd2d22f300c2d3f45401aa628cb90c2034aeeb5e61e63633fba7395ffcd98888013093e7b9f47a3d2c2d2dad01383612aeeb9a2ccb50abd54e63febab5f63611cd3373b3bcd649be14007c669e1191aa294a689588bc730080e33820221fc0853ccf6f6659869322d1e974aa799ec3719cd398ff9088e65dd70dcb6a7334758e3117409b88aa46442699d92b74c789ce97e88b73a3aad5b32251abd5a6549555754744be3e8979634c73dcf9734812434455660e8c88548b0fe4ac7f9d1689e170f85a2444245555df5afb7d9ee79f7efbedb7eb4b4b4b97bbddee35003f87f9d207666647558d2952878b15ee4ce781d723a1aa53aa9aedefef6f2f2d2dfdfaead5abdf4f4e4e368868838842004f0783c1170f1e3ce0adadad3baafaae88cc8ac81b313ff63d15ed1a1811e122cc6f2425c7a53046aad6cfb22cb0d6cae3c78f5fddbe7dfb5f9acde6ff02f09224e96f6c6cac6d6f6fcf596b3d11098d31524ae8f3323f7e6d1a85418d88b0b596c675fd79ad94bc9ee74144988860add58d8d8deecacacad6b56bd7e0baaed3ed76b1bcbc9c361a8d1911d172951e5fadcf63473595aa9231c61cbab17e530085aab4d3d3d349bd5e8fadb5c9f6f676caccd9679f7d06cff300009d4e27bb71e346aaaaf1fefe7ebcbebe6ef7f7f79124c9b9556c69e30d04e3799e4dd394d3347d2306c6b54dbbddc6c58b173131310100b4bebe5e79f4e851633018844992b8beeff7f7f6f6d66fddbac5c618eceeee8288e0ba2e4ed24e670128e68d1acff32c1161381cea9beaf9b11596ebf5bae3388e9f6599bfb1b17131cbb23fb7d65e765d3754d5b524493e4b9224f03c2facd56afee5cb97b952a900385bc51e356686ebba6a8c8109c3302122d3eff7cb3ee6b9991fd336db22b21ac7f1abd5d5d5f6fefefe4c9ee71f89c83b0002004f9324e93e7cf8706f616161a75aadfe608ca90270ce52b12710a89ee759cff3d4d46ab58888bcbdbd3da7e86bbe09f369ad56eb8ac8d7799e7fbababababeb4b4341f45d17522ba0360b6585f6aa7add8e7bd9f284d44340882340c436b1a8dc6569ee755113910514727f3f83d6cad563ba42a45e4eb24491eacaeaeae7ff1c5176e1445d755f51611cd8b48b318c2398f76c2988acdf3fcc4488848160441bf56ab0d4dabd55a8be3b82d222d1af5f58f65de719c03e62727274f645e55ef942b6c09dc5a7b2eede4fb7eb5f83d92244196bdded32dba7a691886bbad562b32d56a75d5f7fd240882b92449301c0e5f0b61291d8220c89bcd66da6834368c3127325f6a9b529e5b6bcfa59df23c9fd9dedef6a32832e355b124b52cdbbeefc74110acd76ab52d23224f1cc7b1cd66f37d00d8d9d9790d401901c77152113993f9b17bd883f09f47c51291388e73c1719cea7173b1ec64d46ab5411004cf7cdf7f6698f989ebba5eabd5ea1151deeff73949123ada4dcef31c711c675b5b5b511cc77baf5ebdda5f5e5e3e96f9a3dae62c159ba6e9f0edb7dfde8ee378a7d7ebb5d2343d7651f53c4f5bad169acd66cff7fda7aeeb3e31009ef8bedf745db7cbccd9e6e6e681322d07c9f31cbd5e0f4992e43b3b3b7d6baddfed763fecf57ae171cc1fa76d4ebb9ff8e69b6fb63737374311899224c9b22c3b360b3ccf43bbddd6898989c8f3bcef012c1b00af2a95ca4b557d9624c9db8d466372381c86bd5e0f651eaa2ad234459224bcbbbbeba569da51d53a80ea79f4fc5991e8f7fb3b711c47225231c61cb4ef8908aa5ace3fd4ebf561bd5eefd6ebf517ccfc0cc08f0640668cd9b6d6ae04413039333373db1813aeadad1d0250bcfac52e4a1b806566e338ce1be9f9e32261ad6da96a66ad95429e1f5c5755e1791ea6a6a6d0e974a2300cbf751ce7b1aabe04b06b88c8aaea2e332ffbbe3f31313131a7aa93bd5e8fadb51cc7f178397388c8291d2d2bcd386367397f42240e7d5e326f8c81ebba68369b7ae1c2053b3131b11304c18a31e601800d228acbe6ee1e800744d4a8542abfb0d65e4ed3d437c6f0fafa3aa2283a68310280881cecb2fc1c3d7f3412e578e5354aa0aeeb62727212172e5cb0ad562bab56ab9baeebfe0ec012805de00f1b1c31809f5cd7fd06c0721004adc9c9c9cbccdcccf39c9899e2387e2da54a27ce710ffb9af3e38c976dfa715065ff746a6a0aed76bb1786e10bdff757003c06f00ca3fee80840914619801700feb5e822fc9388d48d311c0401bd7cf912dd6ef710803f859560cae8966dfce9e969743a1d4c4c4c200cc34dc7717e03e037009e02e803c80e008c81d801f0c018e319632e32b3cbcc5322525155725d97e23846922418ef5f9e04e8246d75d444e420df7ddf47b3d9c4f4f4345aadd6a052a96c7b9eb702e0ff304a9d4d223a58a68fee52c6005e017888d13ef0200cc35f8ac85b4110f0eeee2e6d6d6d61676707dd6e17711cbff1f65269e3a04a85db6c36d16eb7d16c3651a954e079deb631e67300ff05e0f718a54e7f7c9c4300882807d053d5e700ee17152634c6a441104c799e57771c873dcf63dff7d1eff79165d9c17164cff7908619bf0d2ccb6ec97c188668341a68b55ada6eb76da552e919633698f92180ff04f05b004f89a88b2376d24e7d0fc00f00520011113d67e6bf0bc3f05d638cdf6834388e630c0603445184288ad0ebf55096dcf22825b188c0719c83b5c2f33c542a1554ab5554ab55846108dff7e1fbbe755d3733c63c07f06b8cd2e62b003f626c67f24c00458eedaa6a8a515a2544e4388eb3e738ce5410046d6b6d2d49122f8a228aa2884e0350dc02be06a056ab69a55281effb4363cc3e33ef02d800b082d184fd3d80b5e3983ff0f5a42f00405505800f6002c02500d700dc51d59b00ae596b3b699a9a2ccbf8a4142aebfb781a8d6dadaa314645648b88be21a2658c1ef6785cb0be05a04f443fef618fb1391117836d00e812d116800d11b92822e5e33621465b44528c6b00704192c5281db3e27c88d164ec15c78bc2e9658c2acd330083d31c3ff0f1ac1f00a38d85c299004003a3884c0298c1e829ad7900978bcf42001500b50210170e77014418a5e476e1e48f18d5f5171855bf6d8c56d83e809c88ce5c704e8dc001cad14079e140a4aaeb0598e9c2b10ca3963717e754bc2f3b1d29464c770be7d6017c8f11eb8f0b001111bdf634ca59f6ff7842afc34e1218470000000049454e44ae426082";

    public static Bitmap getDelBitmap(){
          return byteToBitmap(hexStringToBytes(delS));
    }

//    /**
//     * 把bitmap转化成字节数组
//     *
//     * @param bitmap数据
//     * @return 字节数组数据
//     */
//    public static byte[] bitmapToByte(Bitmap b) {
//        if (b == null) {
//            return null;
//        }
//
//        ByteArrayOutputStream o = new ByteArrayOutputStream();
//        // 压缩成制定的.PNG格式
//        b.compress(Bitmap.CompressFormat.PNG, 100, o);
//        return o.toByteArray();
//    }

    /**
     * 把自己数组转换成Bitmap
     *
     * @param 字节数组数据
     * @return bitmap数据
     */
    public static Bitmap byteToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapFactory
                .decodeByteArray(b, 0, b.length);
    }

//    /**
//     * 把Drawable数据转换成Bitmap
//     *
//     * @param Drawable数据
//     * @return bitmap数据
//     */
//    public static Bitmap drawableToBitmap(Drawable d) {
//        return d == null ? null : ((BitmapDrawable) d).getBitmap();
//    }
//
//    /**
//     * 把 Bitmap 数据转换成 Drawable
//     *
//     * @param bitmap数据
//     * @return Drawable数据
//     */
//    public static Drawable bitmapToDrawable(Bitmap b) {
//        return b == null ? null : new BitmapDrawable(b);
//    }
//
//    /**
//     * 把 Drawable数据转换成 字节数组
//     *
//     * @param Drawable数据
//     * @return 字节数组
//     */
//    public static byte[] drawableToByte(Drawable d) {
//        return bitmapToByte(drawableToBitmap(d));
//    }
//
//    /**
//     * 把字节数组转换成 Drawable
//     *
//     * @param 字节数组
//     * @return Drawable
//     */
//    public static Drawable byteToDrawable(byte[] b) {
//        return bitmapToDrawable(byteToBitmap(b));
//    }
//
//    /**
//     * 根据url获取输入流，返回并关闭输入流
//     *
//     * @param 数据url
//     * @param 读取超时
//     * @return 输入流
//     * @throws MalformedURLException
//     * @throws IOException
//     */
//    public static InputStream getInputStreamFromUrl(String imageUrl,
//                                                    int readTimeOutMillis) {
//        InputStream stream = null;
//        try {
//            URL url = new URL(imageUrl);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            if (readTimeOutMillis > 0) {
//                con.setReadTimeout(readTimeOutMillis);
//            }
//            stream = con.getInputStream();
//        } catch (MalformedURLException e) {
//            closeInputStream(stream);
//            throw new RuntimeException("MalformedURLException occurred. ", e);
//        } catch (IOException e) {
//            closeInputStream(stream);
//            throw new RuntimeException("IOException occurred. ", e);
//        }
//        return stream;
//    }
//
//    /**
//     * 过去url的数据并转换成drawable
//     *
//     * @param 数据的url
//     * @param 读取超时时间
//     * @return
//     */
//    public static Drawable getDrawableFromUrl(String imageUrl,
//                                              int readTimeOutMillis) {
//        InputStream stream = getInputStreamFromUrl(imageUrl, readTimeOutMillis);
//        Drawable d = Drawable.createFromStream(stream, "src");
//        closeInputStream(stream);
//        return d;
//    }
//
//    /**
//     * 通过数据url获取bitmap数据
//     *
//     * @param 图片url
//     * @return bitmap数据
//     */
//    public static Bitmap getBitmapFromUrl(String imageUrl, int readTimeOut) {
//        InputStream stream = getInputStreamFromUrl(imageUrl, readTimeOut);
//        Bitmap b = BitmapFactory.decodeStream(stream);
//        closeInputStream(stream);
//        return b;
//    }

    /**
     * 通过宽高长度缩放图片
     *
     * @param bitmap数据
     * @param 新的宽度
     * @param 新的高度
     * @return 缩放后的bitmap
     */
    public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
        return scaleImage(org, (float) newWidth / org.getWidth(),
                (float) newHeight / org.getHeight());
    }

    /**
     * 通过宽高的倍数缩放图片
     *
     * @param bitmap数据
     * @param 宽度的倍数
     * @param 高度的倍数
     * @return 缩放后的bitmap
     */
    public static Bitmap scaleImage(Bitmap org, float scaleWidth,
                                    float scaleHeight) {
        if (org == null) {
            return null;
        }

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(),
                matrix, true);
    }

//    /**
//     * 关闭输入流
//     *
//     * @param 输入流
//     */
//    private static void closeInputStream(InputStream s) {
//        if (s == null) {
//            return;
//        }
//
//        try {
//            s.close();
//        } catch (IOException e) {
//            throw new RuntimeException("IOException occurred. ", e);
//        }
//    }


    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private  static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
