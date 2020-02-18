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
def test(*args):  # Simply converts to uppercase, but illustrates that multiple string arguments are possible
    return ' '.join(list(args)).upper()
