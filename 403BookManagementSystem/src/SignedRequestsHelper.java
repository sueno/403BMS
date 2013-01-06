import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class SignedRequestsHelper {

	private static final String	HMAC_SHA256_ALGORITHM	= "HmacSHA256";
	private static final String	REQUEST_METHOD			= "GET";
	private static final String	REQUEST_URI				= "/onca/xml";
	private static final String	UTF8_CHARSET			= "UTF-8";

	// be
																										// lowercase
	private final String		awsAccessKeyId			= "AKIAIWAIBB22KSIA35AQ";
																										private final String		awsSecretKey			= "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";
	private final String		endpoint				= "ecs.amazonaws.jp";							// must

	private Mac					mac						= null;
	private SecretKeySpec		secretKeySpec			= null;

	public SignedRequestsHelper() {

		byte[] secretyKeyBytes;
		try {
			secretyKeyBytes = awsSecretKey.getBytes(UTF8_CHARSET);
			secretKeySpec = new SecretKeySpec(secretyKeyBytes,
					HMAC_SHA256_ALGORITHM);
			mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
			mac.init(secretKeySpec);
		} catch (final UnsupportedEncodingException e) {
		} catch (final NoSuchAlgorithmException e) {
		} catch (final InvalidKeyException e) {
		}
	}

	public String sign(Map<String, String> params) {

		params.put("AWSAccessKeyId", awsAccessKeyId);
		params.put("Timestamp", timestamp());

		final SortedMap<String, String> sortedParamMap = new TreeMap<String, String>(
				params);
		final String canonicalQS = canonicalize(sortedParamMap);
		final String toSign = REQUEST_METHOD + "\n" + endpoint + "\n"
				+ REQUEST_URI + "\n" + canonicalQS;

		final String hmac = hmac(toSign);
		final String sig = percentEncodeRfc3986(hmac);
		final String url = "http://" + endpoint + REQUEST_URI + "?"
				+ canonicalQS + "&Signature=" + sig;

		return url;
	}

	private String canonicalize(SortedMap<String, String> sortedParamMap) {

		if (sortedParamMap.isEmpty()) {
			return "";
		}

		final StringBuffer buffer = new StringBuffer();
		final Iterator<Map.Entry<String, String>> iter = sortedParamMap
				.entrySet().iterator();

		while (iter.hasNext()) {
			final Map.Entry<String, String> kvpair = iter.next();
			buffer.append(percentEncodeRfc3986(kvpair.getKey()));
			buffer.append("=");
			buffer.append(percentEncodeRfc3986(kvpair.getValue()));
			if (iter.hasNext()) {
				buffer.append("&");
			}
		}
		final String cannoical = buffer.toString();
		return cannoical;
	}

	private String hmac(String stringToSign) {

		String signature = null;
		byte[] data;
		byte[] rawHmac;
		try {
			data = stringToSign.getBytes(UTF8_CHARSET);
			rawHmac = mac.doFinal(data);
			final Base64 encoder = new Base64();
			signature = new String(encoder.encode(rawHmac));
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(UTF8_CHARSET + " is unsupported!", e);
		}
		return signature;
	}

	private String percentEncodeRfc3986(String s) {

		String out;
		try {
			out = URLEncoder.encode(s, UTF8_CHARSET).replace("+", "%20")
					.replace("*", "%2A").replace("%7E", "~");
		} catch (final UnsupportedEncodingException e) {
			out = s;
		}
		return out;
	}

	private String timestamp() {

		String timestamp = null;
		final Calendar cal = Calendar.getInstance();
		final DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		dfm.setTimeZone(TimeZone.getTimeZone("GMT"));
		timestamp = dfm.format(cal.getTime());
		return timestamp;
	}
}
