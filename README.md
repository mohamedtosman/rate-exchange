# Ktor API Currency Exchange - Mohamed Osman

## Installation

1. Download or clone the project

2. Build and Run

3. Browse to the local IP: 127.0.0.1:8080

## Usage

### Get Latest Rates

1. `/rates/latest` - `GET`

This will get the latest rates for all symbols (aka currencies),
and default base EUR.

Both `symbols` and `base` are optional parameters. You can use them by appending
to previous url as follows:

`/rates/latest?symbols=USD&base=CAD` - `GET`

This will get the latest rate for USD based to CAD.

### Get Historical Rates on Specific Date

`/rates/historical/2019-01-01` - `GET`

This will get historical rates on a specific date.

`base` is an optional parameter. You can use it by appending it
to previous url as follows:

`/rates/historical/2019-01-01?base=USD`

### Get Historical Rates Between Two Dates

`/rates/historical/range?start_at=2018-01-01&end_at=2018-09-01` - `GET`

This will get rates between two 2 dates. These 2 fields are mandatory.

Both `symbols` and `base` are optional parameters. You can use them by appending
to previous url as follows:

`/rates/historical/range?start_at=2018-01-01&end_at=2018-09-01&symbols=USD&base=CAD` - `GET`

## Unit Testing

I did not really have much time to dive deep into testing.
I played around with it and was able to call the server on specific
URLs but I did not have the time to go about the best way to test the response
since we are returning an HTML. Would love to hear your feedback about that specific
part if you have the chance :)