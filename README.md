# RecordMyGlobalIp

`RecordMyGlobalIp` is a Java program that periodically records your global IP address to a file. This file can be saved to a Google Drive directory, allowing you to access your home network from anywhere with an internet connection, even if you have a dynamic IP address.

## Features

- Periodically retrieves and records the global IP address.
- Saves the IP address to a specified file.
- Configurable time interval for checking the IP address.
- Command-line parameters for customization.

## Requirements

- Java Development Kit (JDK)
- Google Guava library

## Installation

1. Clone the repository or download the source code.
2. Ensure you have the JDK installed on your machine.
3. Add Google Guava to your project dependencies. You can download it [here](https://github.com/google/guava) or add it via your build tool (e.g., Maven, Gradle).

## Usage

### Command-Line Arguments

The program accepts the following command-line arguments in the format of `key=value` pairs, separated by commas:

- `timeinterval`: The interval (in milliseconds) at which the program checks and records the global IP address. Default is `60000` (1 minute).
- `absolutepath`: The absolute path to the file where the IP address will be recorded. Default is `globals.txt`.
- `key`: The key under which the IP address will be saved in the file. Default is `GlobalIp`.

### Running the Program

Run the program with the desired parameters:

    ```sh
    java -cp .:path/to/guava.jar com.ip.global.ipRecorder "timeinterval=30000,absolutepath=/path/to/globals.txt,key=GlobalIp"
    ```

### Example

To run the program with a 30-second interval and save the IP address to `/path/to/globals.txt` under the key `GlobalIp`, use the following command:

```sh
java -cp .:path/to/guava.jar com.ip.global.ipRecorder "timeinterval=30000,absolutepath=/path/to/globals.txt,key=GlobalIp"
```


### Contributing

If you have suggestions for improving this project, please submit a pull request or open an issue.

### License
This project is licensed under the BSD 3-Clause License - see the [LICENSE](LICENSE) file for details.


This `README.md` provides an overview of the project, how to install and run it. Adjust any paths or details as necessary for your specific setup.


## Author
- **Burak KOCAMAN**
  - GitHub: [burakkcmn](https://github.com/burakkcmn)
  - Email: [kocaman.burak.bk@gmail.com](mailto:kocaman.burak.bk@gmail.com)


