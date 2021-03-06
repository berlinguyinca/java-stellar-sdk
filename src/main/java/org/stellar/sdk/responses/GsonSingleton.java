package org.stellar.sdk.responses;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import org.stellar.sdk.Asset;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.responses.effects.EffectResponse;
import org.stellar.sdk.responses.operations.OperationResponse;

import java.lang.reflect.Type;
import java.util.Base64;

public class GsonSingleton {
  private static Gson instance = null;

  protected GsonSingleton() {}

  public static Gson getInstance() {
    if (instance == null) {
      TypeToken accountPageType = new TypeToken<Page<AccountResponse>>() {};
      TypeToken assetPageType = new TypeToken<Page<AssetResponse>>() {};
      TypeToken effectPageType = new TypeToken<Page<EffectResponse>>() {};
      TypeToken ledgerPageType = new TypeToken<Page<LedgerResponse>>() {};
      TypeToken offerPageType = new TypeToken<Page<OfferResponse>>() {};
      TypeToken operationPageType = new TypeToken<Page<OperationResponse>>() {};
      TypeToken pathPageType = new TypeToken<Page<PathResponse>>() {};
      TypeToken tradePageType = new TypeToken<Page<TradeResponse>>() {};
      TypeToken tradeAggregationPageType = new TypeToken<Page<TradeAggregationResponse>>() {};
      TypeToken transactionPageType = new TypeToken<Page<TransactionResponse>>() {};

      instance = new GsonBuilder()
                      .registerTypeAdapter(Asset.class, new AssetDeserializer())
                      .registerTypeAdapter(KeyPair.class, new KeyPairTypeAdapter().nullSafe())
                      .registerTypeAdapter(OperationResponse.class, new OperationDeserializer())
                      .registerTypeAdapter(EffectResponse.class, new EffectDeserializer())
                      .registerTypeAdapter(TransactionResponse.class, new TransactionDeserializer())
                      .registerTypeAdapter(accountPageType.getType(), new PageDeserializer<AccountResponse>(accountPageType))
                      .registerTypeAdapter(assetPageType.getType(), new PageDeserializer<AssetResponse>(assetPageType))
                      .registerTypeAdapter(effectPageType.getType(), new PageDeserializer<AccountResponse>(effectPageType))
                      .registerTypeAdapter(ledgerPageType.getType(), new PageDeserializer<LedgerResponse>(ledgerPageType))
                      .registerTypeAdapter(offerPageType.getType(), new PageDeserializer<OfferResponse>(offerPageType))
                      .registerTypeAdapter(operationPageType.getType(), new PageDeserializer<OperationResponse>(operationPageType))
                      .registerTypeAdapter(pathPageType.getType(), new PageDeserializer<PathResponse>(pathPageType))
                      .registerTypeAdapter(tradePageType.getType(), new PageDeserializer<TradeResponse>(tradePageType))
                      .registerTypeAdapter(tradeAggregationPageType.getType(), new PageDeserializer<TradeAggregationResponse>(tradeAggregationPageType))
                      .registerTypeAdapter(transactionPageType.getType(), new PageDeserializer<TransactionResponse>(transactionPageType))
                      .registerTypeHierarchyAdapter(byte[].class,new ByteArrayToBase64TypeAdapter())
                      .create();
    }
    return instance;
  }


    /**
     * taken from
     * https://gist.github.com/orip/3635246
     */
  private static class ByteArrayToBase64TypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
    public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      return Base64.getDecoder().decode(json.getAsString());
    }

    public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(Base64.getEncoder().encodeToString(src));
    }
  }
}
