import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import os

# dirpath = os.getcwd()
# print(dirpath)
data = np.loadtxt(open("values.csv", "rb"), delimiter = ",")


rows = len(data)
cols = len(data[0])


grid = []

rows = len(data)
cols = len(data[0])
grid = [[(0,0) for row in range(rows)]for col in range(cols)]
ydat = []
xdat = []
for row in range(rows):
    temp = []
    for col in range(cols):
        ydat.append(data[col].tolist())
        temp.append(row)
    xdat.append(temp)

plt.ion()
fig = plt.figure()
ax = fig.add_subplot(111)
ax.grid(True)

import time
for row in range(rows-1):
    plt.plot(xdat[row], ydat[row],'r+')
    plt.xlim([0,60])
    plt.ylim(0,500)
    plt.xlabel("iterations")
    plt.ylabel("pBest Values")
    plt.xticks(np.arange(0, 60, 5.0))
    plt.yticks(np.arange(0, 500, 50.0))
    plt.draw()
    plt.pause(0.01)
    plt.clf()
