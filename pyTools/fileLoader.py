import os
from data import *

driveContents = data

parsed = {}

#add folders
for item in driveContents:
    if item['mimeType'] == 'application/vnd.google-apps.folder':
        parsed[item['id']] = {'files': [], 'folders': [], 'this': item}
parsed[driveRoot] = {'files': [], 'folders': [], 'this': driveRoot}

print parsed.keys()
#populate folders with files
for item in driveContents:
    if item['mimeType'] != 'application/vnd.google-apps.folder':
        parsed[item['parents'][0]['id']]['files'].append(item)


hierachical = {}
#attempt to move folders into its parent folders
for item in driveContents:
    if item['mimeType'] == 'application/vnd.google-apps.folder':
        if parsed[item['parents'][0]['id']] and not item['parents'][0]['id'] == item['id']:
            parsed[item['parents'][0]['id']]['folders'].append(parsed[item['id']])
            hierachical[item['parents'][0]['id']] = parsed[item['parents'][0]['id']]

##recursively create a local model of the remote fs
#PATH = "/home/daniel/.aluDrive/drive_sync/"

def writeContent(filesystemRoot, iterator, root):
    iterator = iterator
    print "\nRecursion step " + str(iterator)
    if(root == driveRoot):
        root = ""
        path = PATH
    else:
        path = PATH + root + "/"
    print "Root is: " + path
    for f in filesystemRoot['files']:
        inf = open(path + f['title'], 'w')
        inf.write(f['id'] + "\n" + f['mimeType'] + "\n" + f['parents'][0]['id'])
        inf.close()
    for folder in filesystemRoot['folders']:
        subPath = path + folder['this']['title'] + "/"
        print "Subpath is: " + subPath
        if not os.path.exists(subPath):
            os.makedirs(subPath)
        for File in folder['files']:
            inf = open(subPath + File['title'], 'w')
            inf.write(File['id'] + "\n" + File['mimeType'] + "\n" + File['parents'][0]['id'])
            inf.close()
    for folder in filesystemRoot['folders']:
        if root != "":
            writeContent(folder, iterator +1, root + "/" + folder['this']['title'])
        else:
            writeContent(folder, iterator +1, root + folder['this']['title'])

writeContent(hierachical[driveRoot], 1, driveRoot)
