<h1 align="center">Java client SDK for Accumulate</h1>

This SDK is a client implementation of Accumulate's JSONRPC API. It also implements signing the transactions which is a prerequisite for transaction submissions.

:construction: This project is still UNDER CONSTRUCTION. :construction:

Most API calls should be operational but test coverage needs to be completed to make sure it actually does.

# Top level API
This SDK contains two top-level API classes, a synchronous and a asynchronous one. Their full paths are
- io.accumulatenetwork.sdk.api.v2.AccumulateSyncApi
- io.accumulatenetwork.sdk.api.v2.AccumulateAsyncApi

These classes need to have the endpoint of the Accumulate deamon configured. The endpoint can be passed into the constructor, or when the default constructor is called it will try to take the endpoint from an environment variable called ACC_API. For a local development node this will look like
ACC_API=http://127.0.1.1:26660/v2
````Java
    private AccumulateSyncApi accumulate = new AccumulateSyncApi(); // Will use ACC_API
    private AccumulateSyncApi accumulate = new AccumulateSyncApi("http://127.0.1.1:26660/v2");
````

# API implementation
TODO
Example implementations can be found in **io.accumulatenetwork.sdk.tests.AccumulateTest** & **io.accumulatenetwork.sdk.tests.AccumulateTestAsync**
