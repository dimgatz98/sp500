#!/usr/bin/python3

import sys
import os
from matplotlib import pyplot as plt
import numpy as np
import pandas as pd
import argparse

parser = argparse.ArgumentParser(description='Plot percent changes for increasing intervals from market indices')
parser.add_argument('--csv-file', action='store', type=str, required = True, \
                    help='Name of the csv file')
parser.add_argument('--out-dir', action='store', type=str, required = False, \
                    help='Directory to save figures. Default path = ./figures')
parser.add_argument('--step', action='store', type=int, default = 1, \
                    help='Incremental step of i used to compute percent changes')
parser.add_argument('--bins', action='store', type=int, default = 100, \
                    help='Number of bins to be used for histogram')
parser.add_argument('-k', action='store', type=float, default = 0, \
                    help='x value to highlight in the diagram')
parser.add_argument('--pdf', action='store_true', required = False, \
                    help='Plot only pdf. By default both will be plotted')
parser.add_argument('--cdf', action='store_true', required = False, \
                    help='Plot only cdf. By default both will be plotted')
parser.add_argument('--field', action='store_true', default='SP500', \
                    help='Csv field from wich to extract data.')

args = parser.parse_args()

if args.step is not None and args.step < 0:
    print("Step can not be negative")
    sys.exit(1)

if args.bins is not None and args.bins < 0:
    print("Bins can not be negative")
    sys.exit(1)

if args.pdf and args.cdf:
    print("--pdf and --cdf can not be provided simultanously")
    parser.print_help()
    sys.exit(1)

dirname, _ = os.path.split(os.path.abspath(__file__))
out_dir = args.out_dir if args.out_dir is not None else os.path.join(dirname, 'figures')
if not os.path.exists(out_dir):
    os.makedirs(out_dir)
elif os.path.isfile(out_dir):
    print(f"{out_dir} is a file")
    sys.exit(1)
market_data = pd.read_csv(args.csv_file, sep=",").to_dict(orient='list')

plt.style.use('seaborn-whitegrid')
id = 0
for i in range(1, len(market_data[args.field]) - 1, args.step):
    data = []
    id += 1
    for row in range(len(market_data[args.field]) - i):
        diff = (market_data[args.field][row + i] - market_data[args.field][row]) \
            / market_data[args.field][row]
        data.append(diff)
    count, bins_count = np.histogram(data, bins=args.bins)
    pdf = count / sum(count)
    cdf = np.cumsum(pdf)
    if not args.cdf:
        plt.plot(bins_count[1:], pdf, color="red", label="PDF")
    if not args.pdf:
        plt.plot(bins_count[1:], cdf, label="CDF")
        plt.scatter(args.k, np.interp(args.k, bins_count[1:], cdf), color="green")
    plt.ylabel("probability")
    plt.xlabel("percent change")
    plt.title(f"{i} month(s)")
    plt.legend()
    plt.savefig(os.path.join(out_dir, f"{id}.png"))
    plt.clf()
    
    if (i - 1) % 10 == 0:
        print(f"{round((i - 1) * 100 / 1768, 3)}% complete")
print('\n**Complete**')
