# PythonUDF - Trifacta User Defined Functions Using Python

2020-02-18 Vijay Balasubramaniam

### Why PythonUDF?

Trifacta offers a rich library of data transformations and functions out of the box. They cover a wide variety of use cases, and can be combined through nesting and macros to perform higher-level functions.

Every once in a while, you might encounter a situation where there is no built-in function available to solve your problem. In such cases, you can leverage Trifacta's user defined function (UDF) framework to write your own function in Java. This UDF can then be used just like you would use any other function.

PythonUDF gives you the ability to write Trifacta UDFs in Python. PythonUDF leverages Jython to combine the power of Java and Python, and provides the following advantages:

- Functions are expressed in Python, which is a much simpler language to learn and use than Java
- Python is typically 3x to 10x more compact. Fewer lines of code directly translates to improved developer productivity and fewer bugs
- No compilation necessary. Python is an interpreted language, so you can deploy Python source easily just by copying a .py file
- Python has a 'batteries included' philosophy, and comes with a rich standard library that provides many capabilities out of the box. You can leverage the Python standard library as well as 3rd party pure Python libraries from PyPI when developing your Trifacta UDF
- Additionally you also have access to the Java ecosystem, and can easily import Java libraries too

### Limitations

PythonUDFs inherits some limitations from Java UDFs

- Previews are not available
- Functions are 'map only' and apply to each row of data. Aggregate functions are not supported
- You cannot retain state between function invocations

Additionally, the following limitations apply:

- PythonUDFs typically run slower than Java UDFs
- PythonUDFs are limited to a single string input column as of now [^1]
- No support for numpy and pandas [^2]
- No support for Python 3 [^2]
- Not tested on Spark [^3]

[^1]: Although you are limited to a single string input, the input can contain a complex string that is made up of multiple objects and arrays (eg. CSV, JSON, XML, YAML, etc.), which you can parse using Python. It is possible to add multiple input support in the future.

[^2]: Support for numpy, pandas and Python 3 is lacking at the moment because Jython does not support it. However, the Jython community is actively working on adding this support. See [here](https://stackoverflow.com/questions/36213908/how-can-i-import-pandas-with-jython) and [here](https://stackoverflow.com/questions/2351008/when-will-jython-support-python-3)

[^3]: All testing was performed on the Photon (Trifacta Server) runtime. Spark should also work in theory, but this was not tested

### Examples

To illustrate the simplicity and power of PythonUDF, here are a few examples. By leveraging the Python standard library, many complex operations can be accomplished with 1-2 lines of code. The following code snippet implements 6 functions!

##### PythonUDF.py
```python
def base64encode(s):
  import base64
  return base64.b64encode(s)
def base64decode(s):
  import base64
  return base64.b64decode(s)
def sha256(s):
  import hashlib
  return hashlib.sha256(s).hexdigest()
def sha512(s):
  import hashlib
  return hashlib.sha512(s).hexdigest()
def mask(s):
  return s[-4:].rjust(len(s), "*")
def test(*args): # Simply converts to uppercase, but illustrates that multiple string arguments are possible
    return ' '.join(list(args)).upper()
```

### Usage

### Developing a Python UDF

The workflow for developing a Python UDF is as follows:

- Build the python function. The function should take a string parameter, and return a string as output
- Test the function locally. It is recommended to use Jython for testing if possible, otherwise CPython is ok
- Add your function code to PythonUDF.py
- Deploy the updated PythonUDF.py (including your code) to the Trifacta server in the `/udfs` folder
- Restart Trifacta services: `sudo service trifacta restart`

The above assumes that the PythonUDF jar file is already installed on the Trifacta server. If not, please see installation instructions further below.

### Using a Python UDF

Using a PythonUDF is very similar to using Java UDFs:

- In the recipe editor, click on 'New Step' and choose 'Invoke External Function'
- From the Function drop-down list, select 'PythonUDF'
- Choose the column to apply the function to. As of now, only a single column is supported
- In the Arguments section, enter the function name (eg. mask) as the first argument
- Optionally, give the new column a name

This is illustrated in the screenshots below


### Screenshots

#### Search for the UDF transformation

<img src="https://github.com/vbalasu/PythonUDF/raw/master/media/pythonudf_choose.png" width=500px align="left" />


#### PythonUDF: mask

![mask](https://github.com/vbalasu/PythonUDF/raw/master/media/pythonudf_mask.png)

### More examples

![More examples](https://github.com/vbalasu/PythonUDF/raw/master/media/pythonudf_examples.png)

### Installing PythonUDF jar in a Trifacta system

To install PythonUDF on a Trifacta server, follow these steps:

- Connect to the Trifacta server using `ssh`
- (Optional) Clone the Github repository to your home directory: `git clone git@github.com:vbalasu/PythonUDF.git`
- Create a new directory to hold the udfs: `sudo mkdir /udfs`
- Copy [PythonUDF.jar](https://github.com/vbalasu/PythonUDF/raw/master/PythonUDF.jar) and [PythonUDF.py](https://github.com/vbalasu/PythonUDF/raw/master/PythonUDF.py) to the `/udfs` directory
- Change permissions to make `trifacta` user the owner of this folder and its contents: `sudo chown -R trifacta:trifacta /udfs`
- Log into Trifacta web as an Administrator and go to Admin Settings
- Modify the following admin settings:
  - udf-service.udfPackages: `com.trifacta.trifactaudfs`
  - udf-service.udfCommunicationTimeout: `100000` (increase timeout to 100 seconds)
  - udf-service.additionalJars: `/udfs/PythonUDF.jar` (this is a comma separated list if you are adding to existing jars)
  - feature.enableUDFTransform.enabled: `true`
  - Leave the remaining `udf-service` settings at their defaults. See the Trifacta Developer Guide for more details
  - Save the admin settings. This will restart the Trifacta server
- Remember to refresh the browser, otherwise your UDF won't appear. Clear the browser cache if required

### Resources

All the jar files, python files and source code referenced on this page are available on Github [at this location](https://github.com/vbalasu/PythonUDF)


