# Client Method Examples

## Add Credits
```
public void testAddCredits() throws Exception{
    TestNetClient client = new TestNetClient();
    LiteAccount liteAccount = LiteAccount.generate();
    String response = client.getFaucet(liteAccount.url().string());
    System.out.println("Lite Account Response: "+response);

    AddCreditsArg addCreditsArg = new AddCreditsArg();
    addCreditsArg.setAmount(1000);
    addCreditsArg.setRecipient(liteAccount.url().string());
    String addCreditsResponse = client.addCredits(addCreditsArg,liteAccount);
    System.out.println("Add Credits Response: "+addCreditsResponse);
}
```

## Burn Tokens
```
public void testBurnTokens() throws Exception {
    TestNetClient client = new TestNetClient();
    LiteAccount liteAccount = LiteAccount.generate();
    String response = client.getFaucet(liteAccount.url().string());
    System.out.println("Lite Account Response: "+response);

    BurnTokensArg burnTokensArg = new BurnTokensArg();
    burnTokensArg.setAmount(BigInteger.valueOf(56879));
    String burnTokensResponse = client.burnTokens(burnTokensArg,liteAccount);
    System.out.println("Burn token Response: "+burnTokensResponse);
}
```

## Create Data Account
```
public void testCreateDataAccount() throws Exception {
        TestNetClient client = new TestNetClient();
        LiteAccount liteAccount = LiteAccount.generate();
        String response = client.getFaucet(liteAccount.url().string());
        System.out.println("Lite Account Response: "+response);
        

        String identityUrl = "acc://my-own-identity-1";
        CreateIdentityArg createIdentityArg = new CreateIdentityArg();
        createIdentityArg.setUrl(identityUrl);
        TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
        createIdentityArg.setPublicKey(kp.getPublicKey());
        createIdentityArg.setKeyBookName("test-key-book");
        createIdentityArg.setKeyPageName("test-key-page");
        String createAdiResponse = client.createIdentity(createIdentityArg,liteAccount);
        System.out.println("Create ADI Response: "+createAdiResponse);
        
        ADI adi = new ADI(AccURL.toAccURL(identityUrl),kp);

        CreateDataAccountArg createDataAccountArg = new CreateDataAccountArg();
        createDataAccountArg.setUrl(identityUrl+"/data");
        createDataAccountArg.setKeyBookUrl(identityUrl+"/test-key-book");
        createDataAccountArg.setManagerKeyBookUrl(identityUrl+"/test-key-book");
        createDataAccountArg.setScratch(true);
        String createDataAccountResponse = client.createDataAccount(createDataAccountArg,adi);
        System.out.println("Create data account Response: "+createDataAccountResponse);
    }
```

## Create ADI
```
public void testCreateADI() throws Exception {
    TestNetClient client = new TestNetClient();
    LiteAccount liteAccount = LiteAccount.generate();
    String response = client.getFaucet(liteAccount.url().string());
    System.out.println("Lite Account Response: "+response);

    CreateIdentityArg createIdentityArg = new CreateIdentityArg();
    createIdentityArg.setUrl("acc://my-own-identity");
    TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
    createIdentityArg.setPublicKey(kp.getPublicKey());
    createIdentityArg.setKeyBookName("test-key-book");
    createIdentityArg.setKeyPageName("test-key-page");
    String createAdiResponse = client.createIdentity(createIdentityArg,liteAccount);
    System.out.println("Create ADI Response: "+createAdiResponse);
}
```

## Create Key Book
```
public void testCreateKeyBook() throws Exception{
    TestNetClient client = new TestNetClient();
    LiteAccount liteAccount = LiteAccount.generate();
    String response = client.getFaucet(liteAccount.url().string());
    System.out.println("Lite Account Response: "+response);

    String identityUrl = "acc://my-own-identity-1";
    CreateIdentityArg createIdentityArg = new CreateIdentityArg();
    createIdentityArg.setUrl(identityUrl);
    TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
    createIdentityArg.setPublicKey(kp.getPublicKey());
    createIdentityArg.setKeyBookName("test-key-book");
    createIdentityArg.setKeyPageName("test-key-page");
    String createAdiResponse = client.createIdentity(createIdentityArg,liteAccount);
    System.out.println("Create ADI Response: "+createAdiResponse);
    ADI adi = new ADI(AccURL.toAccURL(identityUrl),kp);

    CreateKeyBookArg createKeyBookArg = new CreateKeyBookArg();
    createKeyBookArg.setUrl(identityUrl+"/book1");
    List<String> pages = new ArrayList<>();
    pages.add(identityUrl+"/test-key-page");
    createKeyBookArg.setPages(pages);
    String createKeyBook = client.createKeyBook(createKeyBookArg,adi);
    System.out.println("Create Key book Response: "+createKeyBook);
}
```

## Create Key Page
```
public void testCreateKeyPage() throws Exception{
    TestNetClient client = new TestNetClient();
    LiteAccount liteAccount = LiteAccount.generate();
    String response = client.getFaucet(liteAccount.url().string());
    System.out.println("Lite Account Response: "+response);

    String identityUrl = "acc://my-own-identity-1";
    CreateIdentityArg createIdentityArg = new CreateIdentityArg();
    createIdentityArg.setUrl(identityUrl);
    TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
    createIdentityArg.setPublicKey(kp.getPublicKey());
    createIdentityArg.setKeyBookName("test-key-book");
    createIdentityArg.setKeyPageName("test-key-page");
    String createAdiResponse = client.createIdentity(createIdentityArg,liteAccount);
    System.out.println("Create ADI Response: "+createAdiResponse);
    ADI adi = new ADI(AccURL.toAccURL(identityUrl),kp);

    CreateKeyPageArg createKeyPageArg = new CreateKeyPageArg();
    List<byte[]> keys = new ArrayList<>();
    keys.add(liteAccount.getPublicKey());
    createKeyPageArg.setKeys(keys);
    createKeyPageArg.setUrl(identityUrl+"/test-key-page");
    String createKeyPageResponse = client.createKeyPage(createKeyPageArg,adi);
    System.out.println("Create Key page Response: "+createKeyPageResponse);
}
```

## Create Token Account
```
public void testCreateTokenAccount() throws Exception {
    TestNetClient client = new TestNetClient();
    LiteAccount liteAccount = LiteAccount.generate();
    String response = client.getFaucet(liteAccount.url().string());
    System.out.println("Lite Account Response: "+response);

    String identityUrl = "acc://my-own-identity-2";
    CreateIdentityArg createIdentityArg = new CreateIdentityArg();
    createIdentityArg.setUrl(identityUrl);
    TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
    createIdentityArg.setPublicKey(kp.getPublicKey());
    createIdentityArg.setKeyBookName("test-key-book");
    createIdentityArg.setKeyPageName("test-key-page");
    String createAdiResponse = client.createIdentity(createIdentityArg,liteAccount);
    System.out.println("Create ADI Response: "+createAdiResponse);
    ADI adi = new ADI(AccURL.toAccURL(identityUrl),kp);

    CreateTokenAccountArg createTokenAccountArg = new CreateTokenAccountArg();
    createTokenAccountArg.setUrl(identityUrl);
    createTokenAccountArg.setTokenUrl(identityUrl+"/tok");
    createTokenAccountArg.setKeyBookUrl(identityUrl+"/test-key-book");
    createTokenAccountArg.setScratch(true);
    String createTokenAccountResponse = client.createTokenAccount(createTokenAccountArg,adi);
    System.out.println("Create Token Account Response: "+createTokenAccountResponse);
}
```

## Create Token
```
public void testCreateToken() throws Exception {
    TestNetClient client = new TestNetClient();
    LiteAccount liteAccount = LiteAccount.generate();
    String response = client.getFaucet(liteAccount.url().string());
    System.out.println("Lite Account Response: "+response);

    AddCreditsArg addCreditsArg = new AddCreditsArg();
    addCreditsArg.setAmount(500000);
    addCreditsArg.setRecipient(liteAccount.url().string());
    String addCreditsResponse = client.addCredits(addCreditsArg,liteAccount);
    System.out.println("Add Credits Response: "+addCreditsResponse);

    String identityUrl = "acc://my-own-identity-3";
    CreateIdentityArg createIdentityArg = new CreateIdentityArg();
    createIdentityArg.setUrl(identityUrl);
    TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
    createIdentityArg.setPublicKey(kp.getPublicKey());
    createIdentityArg.setKeyBookName("test-key-book");
    createIdentityArg.setKeyPageName("test-key-page");
    String createAdiResponse = client.createIdentity(createIdentityArg,liteAccount);
    System.out.println("Create ADI Response: "+createAdiResponse);
    Thread.sleep(5000);
    ADI adi = new ADI(AccURL.toAccURL(identityUrl),kp);

    CreateTokenArg createTokenArg = new CreateTokenArg();
    createTokenArg.setUrl(identityUrl+"/tok");
    createTokenArg.setKeyBookUrl(identityUrl+"/test-key-page");
    createTokenArg.setSymbol("INR");
    createTokenArg.setPrecision(10);
    createTokenArg.setProperties("acc://my-own-identity-3/INR");
    createTokenArg.setInitialSupply(BigInteger.valueOf(1000000000));
    createTokenArg.setHasSupplyLimit(true);
    createTokenArg.setManager("acc://my-own-identity-3/test-key-page");
    String createTokenResponse = client.createToken(createTokenArg,adi);
    System.out.println("Create Token Response: "+createTokenResponse);
}
```

## Issue Token
```
public void testIssueTokens() throws Exception {
    TestNetClient client = new TestNetClient();

    LiteAccount liteAccount = LiteAccount.generate();
    String response = client.getFaucet(liteAccount.url().string());
    System.out.println("Lite Account Response: "+response);

    LiteAccount liteAccount2 = LiteAccount.generate();
    String response2 = client.getFaucet(liteAccount2.url().string());
    System.out.println("Lite Account Response: "+response2);

    IssueTokensArg issueTokensArg = new IssueTokensArg();
    issueTokensArg.setRecipient(liteAccount2.url().string());
    issueTokensArg.setAmount(1);
    String issueTokensResponse = client.issueTokens(issueTokensArg,liteAccount);
    System.out.println("Issue Token  Response: "+issueTokensResponse);
}
```

## Send Token
```
public void testSendTokens() throws Exception {
    TestNetClient client = new TestNetClient();

    LiteAccount liteAccount = LiteAccount.generate();
    String response = client.getFaucet(liteAccount.url().string());
    System.out.println("Lite Account Response: "+response);

    LiteAccount liteAccount2 = LiteAccount.generate();
    String response2 = client.getFaucet(liteAccount2.url().string());
    System.out.println("Lite Account Response: "+response2);

    SendTokensArg sendTokensArg = new SendTokensArg();
    List<TokenRecipientArg> to = new ArrayList<>();
    TokenRecipientArg tokenRecipientArg = new TokenRecipientArg();
    tokenRecipientArg.setAmount(20000000);
    tokenRecipientArg.setUrl(liteAccount2.url().string());
    to.add(tokenRecipientArg);
    sendTokensArg.setTo(to);
    String sendTokensResponse = client.sendToken(sendTokensArg,liteAccount);
    System.out.println("Send Token  Response: "+sendTokensResponse);
}
```

## Update Key Page
```
public void testUpdateKeyPage() throws Exception {
    TestNetClient client = new TestNetClient();

    LiteAccount liteAccount = LiteAccount.generate();
    String response = client.getFaucet(liteAccount.url().string());
    System.out.println("Lite Account Response: "+response);

    CreateIdentityArg createIdentityArg = new CreateIdentityArg();
    createIdentityArg.setUrl("acc://my-own-identity");
    TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
    createIdentityArg.setPublicKey(kp.getPublicKey());
    createIdentityArg.setKeyBookName("test-key-book");
    createIdentityArg.setKeyPageName("test-key-page");
    String createAdiResponse = client.createIdentity(createIdentityArg,liteAccount);
    System.out.println("Create ADI Response: "+createAdiResponse);

    UpdateKeyPageArg updateKeyPageArg = new UpdateKeyPageArg();
    updateKeyPageArg.setOwner("acc://my-own-identity");
    TweetNaclFast.Signature.KeyPair kp1 = TweetNaclFast.Signature.keyPair();
    updateKeyPageArg.setNewKey(kp1.getPublicKey());
    updateKeyPageArg.setKey(kp.getPublicKey());
    updateKeyPageArg.setOperation(KeyPageOperation.UpdateKey);
    String updateKeyPageResponse = client.updateKeyPage(updateKeyPageArg,liteAccount);
    System.out.println("update KeyPage Response  Response: "+updateKeyPageResponse);
}
```

## Write Data
```
public void testWriteData() throws Exception {
    TestNetClient client = new TestNetClient();
    LiteAccount liteAccount = LiteAccount.generate();
    String response = client.getFaucet(liteAccount.url().string());
    System.out.println("Lite Account Response: "+response);

    String identityUrl = "acc://my-own-identity-1";
    CreateIdentityArg createIdentityArg = new CreateIdentityArg();
    createIdentityArg.setUrl(identityUrl);
    TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
    createIdentityArg.setPublicKey(kp.getPublicKey());
    createIdentityArg.setKeyBookName("test-key-book");
    createIdentityArg.setKeyPageName("test-key-page");
    String createAdiResponse = client.createIdentity(createIdentityArg,liteAccount);
    System.out.println("Create ADI Response: "+createAdiResponse);
    ADI adi = new ADI(AccURL.toAccURL(identityUrl),kp);

    WriteDataArg writeDataArg = new WriteDataArg();
    writeDataArg.setData("Test".getBytes(StandardCharsets.UTF_8));
    List<byte[]> bytes = new ArrayList<>();
    bytes.add(new byte[0]);
    writeDataArg.setExtIds(bytes);
    String writeDataResponse = client.writeData(writeDataArg,adi);
    System.out.println("Write data Response  Response: "+writeDataResponse);
}
```
